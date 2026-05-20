package com.example.stock_backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "companies")
public class Company {

    @Id
    private String id; 
    private String name; 
    private double price;
    private int stocks = 300000;
    private double change = 0;
    private String picture;
    private int direction = 0;
}
