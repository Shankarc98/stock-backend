package com.example.stock_backend.dto;

import com.example.stock_backend.model.Player;

public class LoginResponse {
    
    String token; 
    String name; 
    Player player;

    public LoginResponse(){

    }
    public LoginResponse(String token, String name, Player player){
        this.name = name;
        this.token = token;
        this.player = player;
    }

    public String getToken(){
        return token;
    }
    public String getName(){
        return name;
    }
    public Player getPlayer(){
        return player;
    }

    public void setToken(String token){
        this.token = token; 
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPlayer(Player player){
        this.player = player;
    }
}
