package com.example.stock_backend.dto;

import lombok.Data; 

@Data
public class StockUpdateRequest {
    private int numOfStocks;
    private String companyId; 

    public int getNumOfStocks(){
        return numOfStocks;
    }

    public String getCompanyId(){
        return companyId; 
    }
}
