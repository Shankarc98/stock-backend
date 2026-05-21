package com.example.stock_backend.dto;

import lombok.Data; 

public class PriceRequest {
    private double price;    
    
    public double getPrice() {
        return price;
    }
}
