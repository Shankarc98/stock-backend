package com.example.stock_backend.dto;

import java.util.List; 
import com.example.stock_backend.model.Company; 

public class MarketResponse {
    
    private List<Company> companies; 

    private long nextUpdateTime; 

    public MarketResponse(List<Company> companies, long nextUpdateTime){
        this.companies = companies; 
        this.nextUpdateTime = nextUpdateTime; 
    }
    public void setCompanies(List<Company> companies){
        this.companies = companies;
    }
    public void setUpdateTime(long nextUpdateTime){
        this.nextUpdateTime = nextUpdateTime; 
    }
    public List<Company> getCompanies(){
        return companies; 
    }
    public long getNextUpdateTime(){
        return nextUpdateTime; 
    }
}
