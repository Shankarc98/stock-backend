package com.example.stock_backend.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController; 

import com.example.stock_backend.dto.StockUpdateRequest;
import com.example.stock_backend.service.TradeService;


@RestController
public class TradeController {
    
    @Autowired TradeService ts; 

    @PutMapping("/trade/{id}/buy")
    public ResponseEntity<?> buyStocks(@PathVariable String id, @RequestBody StockUpdateRequest request){
        try {
            return ResponseEntity.ok(ts.buyStocks(id, request.getNumOfStocks(), request.getCompanyId()));
        } catch (ResponseStatusException exception) {
            return ResponseEntity.status(exception.getStatusCode())
                .body(Map.of("message", exception.getReason()));
        }
    }

    @PutMapping("/trade/{id}/sell")
    public ResponseEntity<?> sellStocks(@PathVariable String id, @RequestBody StockUpdateRequest request){
        try {
            return ResponseEntity.ok(ts.sellStocks(id, request.getNumOfStocks(), request.getCompanyId()));
        } catch (ResponseStatusException exception) {
            return ResponseEntity.status(exception.getStatusCode())
                .body(Map.of("message", exception.getReason()));
        }
    }
}
