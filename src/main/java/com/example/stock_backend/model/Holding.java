package com.example.stock_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; 

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Holding {
    private String companyName; 
    private int numOfStocks;     
}
