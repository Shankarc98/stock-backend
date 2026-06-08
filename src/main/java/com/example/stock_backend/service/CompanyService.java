package com.example.stock_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.stock_backend.model.Company;
import com.example.stock_backend.repository.CompanyRepository;
import com.example.stock_backend.dto.MarketResponse; 
import com.example.stock_backend.repository.TimeRepository; 
import com.example.stock_backend.model.UpdateTime; 

import org.springframework.http.ResponseEntity;

@Service
public class CompanyService {
    
    
    private final CompanyRepository cr;
    private final TimeRepository tr;  

    public CompanyService(CompanyRepository cr, TimeRepository tr){
        this.cr = cr; 
        this.tr = tr; 
    }
    public Company createCompany(Company c){

        return cr.save(c);
    }
    public ResponseEntity<MarketResponse> getAllCompanies(){

        UpdateTime time = tr.findById("timeId").orElse(new UpdateTime()); 

        MarketResponse mr = new MarketResponse(cr.findAll(), time.getNextUpdateTime());
        return ResponseEntity.ok(mr);
        
    }
    public Company updatePrice(String id, double price){
        Company c = cr.findById(id).orElse(null);
        double currentPrice = c.getPrice();

        if(currentPrice >= 3){
            c.setPrice(currentPrice + price);
        }
        
        return cr.save(c); 
    }
}
