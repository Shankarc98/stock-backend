package com.example.stock_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
        
    private String id;
    private String trade; 
    private String company; 
    private int sharesTraded; 
    private double price; 
    private double totalMoney; 
}
