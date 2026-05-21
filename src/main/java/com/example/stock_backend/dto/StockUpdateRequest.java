package com.example.stock_backend.dto;

import lombok.Data; 

@Data
public class StockUpdateRequest {
    private int numOfStocks;
    private float price;  
    private String companyName; 
    private String companyId; 

    public int getNumOfStocks(){
        return numOfStocks;
    }

    public float getPrice(){
        return price;
    }

    public String getCompanyName(){
        return companyName;
    }
    
    public String getCompanyId(){
        return companyId; 
    }
}
