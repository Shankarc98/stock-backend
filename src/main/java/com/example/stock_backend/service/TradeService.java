package com.example.stock_backend.service;

import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.stock_backend.dto.TradeResponse;
import com.example.stock_backend.model.Company;
import com.example.stock_backend.model.Holding;
import com.example.stock_backend.model.Player;
import com.example.stock_backend.model.Transaction;
import com.example.stock_backend.repository.CompanyRepository;
import com.example.stock_backend.repository.PlayerRepository;

@Service
public class TradeService {
    private static final int MAX_TRADE_QUANTITY = 300_000;

    private final PlayerRepository pr;
    private final CompanyRepository cr;

    public TradeService(PlayerRepository pr, CompanyRepository cr){
        this.pr = pr;
        this.cr = cr;
    }

    public TradeResponse buyStocks(String id, int stocks, String companyId){
        validateQuantity(stocks);
        Player player = findPlayer(id);
        Company company = findCompany(companyId);
        BigDecimal price = currentPrice(company);
        BigDecimal total = tradeTotal(price, stocks);
        BigDecimal availableMoney = money(player);

        if (total.compareTo(availableMoney) > 0){
            throw badRequest(String.format(
                "Insufficient balance. Required ₹%.2f, available ₹%.2f.",
                total.doubleValue(), availableMoney.doubleValue()
            ));
        }
        if (stocks > company.getStocks()){
            throw badRequest("Not enough shares are available to buy.");
        }

        List<Holding> holdings = holdings(player);
        Holding holding = holdings.stream()
            .filter(item -> company.getName().equals(item.getCompanyName()))
            .findFirst()
            .orElseGet(() -> {
                Holding newHolding = new Holding(company.getName(), 0);
                holdings.add(newHolding);
                return newHolding;
            });

        holding.setNumOfStocks(holding.getNumOfStocks() + stocks);
        player.setMoney(availableMoney.subtract(total).doubleValue());
        company.setStocks(company.getStocks() - stocks);
        recordTransaction(player, company, stocks, "BOUGHT", price, total);

        return saveTrade(player, company);
    }

    public TradeResponse sellStocks(String id, int stocks, String companyId){
        validateQuantity(stocks);
        Player player = findPlayer(id);
        Company company = findCompany(companyId);
        BigDecimal price = currentPrice(company);
        BigDecimal newBalance = money(player).add(tradeTotal(price, stocks));
        List<Holding> holdings = holdings(player);
        long available = holdings.stream()
            .filter(item -> company.getName().equals(item.getCompanyName()))
            .mapToLong(Holding::getNumOfStocks)
            .sum();

        if (stocks > available){
            throw badRequest("Not enough shares to sell. Available quantity: " + available + ".");
        }
        if ((long) company.getStocks() + stocks > MAX_TRADE_QUANTITY){
            throw badRequest("The company cannot accept more shares at this time.");
        }

        int remaining = stocks;
        for (Holding holding : holdings){
            if (!company.getName().equals(holding.getCompanyName()) || holding.getNumOfStocks() <= 0) continue;
            int sold = Math.min(remaining, holding.getNumOfStocks());
            holding.setNumOfStocks(holding.getNumOfStocks() - sold);
            remaining -= sold;
            if (remaining == 0) break;
        }

        player.setMoney(newBalance.doubleValue());
        company.setStocks(company.getStocks() + stocks);
        recordTransaction(player, company, stocks, "SOLD", price, tradeTotal(price, stocks));
        return saveTrade(player, company);
    }

    private void validateQuantity(int quantity){
        if (quantity <= 0 || quantity > MAX_TRADE_QUANTITY){
            throw badRequest("Enter a whole number of shares between 1 and 300000.");
        }
    }

    private Player findPlayer(String id){
        return pr.findById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found.")
        );
    }

    private Company findCompany(String id){
        if (id == null || id.isBlank()){
            throw badRequest("Select a valid company.");
        }
        return cr.findById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found.")
        );
    }

    private BigDecimal currentPrice(Company company){
        if (company.getName() == null || company.getName().isBlank()
            || !Double.isFinite(company.getPrice()) || company.getPrice() <= 0){
            throw badRequest("The current share price is unavailable.");
        }
        return BigDecimal.valueOf(company.getPrice());
    }

    private BigDecimal tradeTotal(BigDecimal price, int quantity){
        return price.multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal money(Player player){
        if (!Double.isFinite(player.getMoney()) || player.getMoney() < 0){
            throw badRequest("The available wallet balance is invalid.");
        }
        return BigDecimal.valueOf(player.getMoney()).setScale(2, RoundingMode.HALF_UP);
    }

    private List<Holding> holdings(Player player){
        if (player.getStocksHeld() == null){
            player.setStocksHeld(new ArrayList<>());
        }
        return player.getStocksHeld();
    }

    private void recordTransaction(
        Player player,
        Company company,
        int quantity,
        String trade,
        BigDecimal price,
        BigDecimal total
    ){
        if (player.getTransactions() == null){
            player.setTransactions(new ArrayList<>());
        }
        player.getTransactions().add(new Transaction(
            UUID.randomUUID().toString(),
            trade,
            company.getName(),
            quantity,
            price.doubleValue(),
            total.doubleValue()
        ));
    }

    private TradeResponse saveTrade(Player player, Company company){
        return new TradeResponse(pr.save(player), cr.save(company));
    }

    private ResponseStatusException badRequest(String message){
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
    }
}
