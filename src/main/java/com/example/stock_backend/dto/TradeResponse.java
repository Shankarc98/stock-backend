package com.example.stock_backend.dto;

import com.example.stock_backend.model.Company;
import com.example.stock_backend.model.Player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradeResponse {
    private Player p;
    private Company c; 

}
