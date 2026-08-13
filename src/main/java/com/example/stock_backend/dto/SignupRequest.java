package com.example.stock_backend.dto;

import lombok.Data; 

@Data
public class SignupRequest {
    private String name;
    private String password;

    public String getName(){
        return name;
    }
    public String getPassword(){
        return password;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setPassword(String password){
        this.password = password;
    }
}
