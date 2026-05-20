package com.example.stock_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

import com.example.stock_backend.dto.TradeResponse;
import com.example.stock_backend.model.Company;
import com.example.stock_backend.model.Holding;
import com.example.stock_backend.model.Player;
import com.example.stock_backend.repository.CompanyRepository;
import com.example.stock_backend.repository.PlayerRepository;

@Service
public class TradeService {
    @Autowired PlayerRepository pr; 
    @Autowired CompanyRepository cr; 

    public TradeResponse buyStocks(String id, int stocks, float price, String name, String companyId){
        Player p = pr.findById(id).orElseThrow(); 
        Company c = cr.findById(companyId).orElseThrow(); 

        List<Holding> currentHolding = p.getStocksHeld();
        
        for(Holding h : currentHolding){
            if(h.getCompanyName().equals(name) && p.getMoney() >= (price * stocks) && c.getStocks() >= stocks){
                h.setNumOfStocks(h.getNumOfStocks() + stocks);
                p.setMoney(p.getMoney() - (price * stocks));
                c.setStocks(c.getStocks() - stocks);
                 
                return new TradeResponse(pr.save(p), cr.save(c)); 
            }
        }
         
        return new TradeResponse(p,c); 
    }

    public TradeResponse sellStocks(String id, int stocks, float price, String name, String companyId){
        Player p = pr.findById(id).orElseThrow(); 
        Company c = cr.findById(companyId).orElseThrow(); 

        List<Holding> currentHolding = p.getStocksHeld(); 

        for(Holding h : currentHolding){
            if(h.getCompanyName().equals(name) && h.getNumOfStocks() + stocks <= 300000 && h.getNumOfStocks() - stocks >= 0){
                h.setNumOfStocks(h.getNumOfStocks() - stocks);
                p.setMoney(p.getMoney() + (price * stocks));
                c.setStocks(c.getStocks() + stocks);
                return new TradeResponse(pr.save(p), cr.save(c));                
                
            }
        }
        return new TradeResponse(p,c);
    }
    
}
