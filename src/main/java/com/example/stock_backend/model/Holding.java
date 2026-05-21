package com.example.stock_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; 

@Data
public class Holding {
    private String companyName; 
    private int numOfStocks;     

    public Holding(){
    }

    public Holding(String companyName, int numOfStocks){
        this.companyName = companyName;
        this.numOfStocks = numOfStocks; 
    }

    public String getCompanyName(){
        return companyName;
    }
    public int getNumOfStocks(){
        return numOfStocks; 
    }
    public void setCompanyName(String name){
        companyName = name;
    }
    public void setNumOfStocks(int num){
        numOfStocks = num; 
    }

}
