package com.example.stock_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.stock_backend.model.Company;
import com.example.stock_backend.repository.CompanyRepository; 
import org.springframework.http.ResponseEntity;

@Service
public class CompanyService {
    
    @Autowired
    private CompanyRepository cr; 

    public Company createCompany(Company c){

        return cr.save(c);
    }
    public ResponseEntity<List<Company>> getAllCompanies(){
        return ResponseEntity.ok(cr.findAll());
    }
    public Company updatePrice(String id, float price){
        Company c = cr.findById(id).orElse(null);
        double currentPrice = c.getPrice();

        if(currentPrice >= 3){
            c.setPrice(currentPrice + price);
        }

        return cr.save(c); 
    }
}
