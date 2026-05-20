package com.example.stock_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController; 

import com.example.stock_backend.dto.StockUpdateRequest;
import com.example.stock_backend.dto.TradeResponse;
import com.example.stock_backend.service.TradeService;


@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class TradeController {
    
    @Autowired TradeService ts; 

    @PutMapping("/trade/{id}/buy")
    public TradeResponse buyStocks(@PathVariable String id, @RequestBody StockUpdateRequest request){
        return ts.buyStocks(id, request.getNumOfStocks(), request.getPrice(), request.getCompanyName(),  request.getCompanyId());
    }

    @PutMapping("/trade/{id}/sell")
    public TradeResponse sellStocks(@PathVariable String id, @RequestBody StockUpdateRequest request){
        return ts.sellStocks(id, request.getNumOfStocks(), request.getPrice(), request.getCompanyName(), request.getCompanyId());
    }
}
