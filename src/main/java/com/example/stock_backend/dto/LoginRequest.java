package com.example.stock_backend.dto;

import lombok.Data; 

@Data
public class LoginRequest {
    
    private String name; 
    private String password;

    public String getName(){
        return name;
    }
    public String getPassword(){
        return password;
    }
}
