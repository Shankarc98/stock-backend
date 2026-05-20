package com.example.stock_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.stock_backend.dto.PriceRequest;
import com.example.stock_backend.model.Company;
import com.example.stock_backend.service.CompanyService; 

@RestController
public class CompanyControlller {
    
    @Autowired 
    private CompanyService cs; 

    @GetMapping("/company")
    public ResponseEntity<List<Company>> getAllCompanies(){
        return cs.getAllCompanies(); 
    }

    @PostMapping("/company")
    public Company createCompany(@RequestBody Company c){
        return cs.createCompany(c); 
    }

    @PutMapping("/company/{id}/price") 
    public Company updatePrice(@PathVariable String id, @RequestBody PriceRequest request){
        return cs.updatePrice(id, request.getPrice());
    }
}
