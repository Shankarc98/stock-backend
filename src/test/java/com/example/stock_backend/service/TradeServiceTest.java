package com.example.stock_backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.stock_backend.model.Company;
import com.example.stock_backend.model.Holding;
import com.example.stock_backend.model.Player;
import com.example.stock_backend.repository.CompanyRepository;
import com.example.stock_backend.repository.PlayerRepository;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {
    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private CompanyRepository companyRepository;

    private TradeService tradeService;
    private Player player;
    private Company company;

    @BeforeEach
    void setUp(){
        tradeService = new TradeService(playerRepository, companyRepository);
        player = new Player();
        player.setId("player-id");
        player.setMoney(100);
        player.setStocksHeld(new ArrayList<>(List.of(new Holding("Example", 4))));
        company = new Company("Example", 25.50, 0, "");
        company.setId("company-id");
        company.setStocks(100);

        when(playerRepository.findById("player-id")).thenReturn(Optional.of(player));
        when(companyRepository.findById("company-id")).thenReturn(Optional.of(company));
    }

    @Test
    void rejectsBuyBeyondWalletWithoutSaving(){
        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> tradeService.buyStocks("player-id", 4, "company-id")
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals(100, player.getMoney());
        assertEquals(4, player.getStocksHeld().get(0).getNumOfStocks());
        assertEquals(100, company.getStocks());
        verify(playerRepository, never()).save(any(Player.class));
        verify(companyRepository, never()).save(any(Company.class));
    }

    @Test
    void rejectsSellBeyondHoldingsWithoutSaving(){
        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> tradeService.sellStocks("player-id", 5, "company-id")
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals(100, player.getMoney());
        assertEquals(4, player.getStocksHeld().get(0).getNumOfStocks());
        assertEquals(100, company.getStocks());
        verify(playerRepository, never()).save(any(Player.class));
        verify(companyRepository, never()).save(any(Company.class));
    }

    @Test
    void pricesAndPersistsValidBuyUsingCurrentCompanyPrice(){
        when(playerRepository.save(any(Player.class))).thenAnswer(call -> call.getArgument(0));
        when(companyRepository.save(any(Company.class))).thenAnswer(call -> call.getArgument(0));

        var result = tradeService.buyStocks("player-id", 2, "company-id");

        assertEquals(49, result.getP().getMoney());
        assertEquals(6, result.getP().getStocksHeld().get(0).getNumOfStocks());
        assertEquals(98, result.getC().getStocks());
        assertEquals(1, result.getP().getTransactions().size());
        assertEquals("BOUGHT", result.getP().getTransactions().get(0).getTrade());
        assertEquals(51, result.getP().getTransactions().get(0).getTotalMoney());
        verify(playerRepository).save(player);
        verify(companyRepository).save(company);
    }

    @Test
    void persistsValidSaleUsingCurrentCompanyPrice(){
        when(playerRepository.save(any(Player.class))).thenAnswer(call -> call.getArgument(0));
        when(companyRepository.save(any(Company.class))).thenAnswer(call -> call.getArgument(0));

        var result = tradeService.sellStocks("player-id", 2, "company-id");

        assertEquals(151, result.getP().getMoney());
        assertEquals(2, result.getP().getStocksHeld().get(0).getNumOfStocks());
        assertEquals(102, result.getC().getStocks());
        assertEquals(1, result.getP().getTransactions().size());
        assertEquals("SOLD", result.getP().getTransactions().get(0).getTrade());
        assertEquals(51, result.getP().getTransactions().get(0).getTotalMoney());
        verify(playerRepository).save(player);
        verify(companyRepository).save(company);
    }
}
