package com.example.stock_backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
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

    public Company(){
    }
    public Company(String name, double price, double change, String picture){
        this.name = name; 
        this.price = price; 
        this.change = change; 
        this.picture = picture; 
    }
    public String getId(){
        return id;
    }
    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }

}
