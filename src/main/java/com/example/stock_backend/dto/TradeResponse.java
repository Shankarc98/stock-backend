package com.example.stock_backend.dto;

import com.example.stock_backend.model.Company;
import com.example.stock_backend.model.Player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class TradeResponse {
    private Player p;
    private Company c; 

    public TradeResponse(Player p, Company c){
        this.p = p; 
        this.c = c; 
    }

}
