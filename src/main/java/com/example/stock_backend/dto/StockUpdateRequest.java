package com.example.stock_backend.dto;

import lombok.Data; 

@Data
public class StockUpdateRequest {
    private int numOfStocks;
    private float price;  
    private String companyName; 
    private String companyId; 
}
