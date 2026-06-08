package com.example.stock_backend.model;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.example.stock_backend.model.Holding;

import lombok.Data; 

@Data
@Document(collection = "players")
public class Player {
    
    @Id    
    private String id;
    private String name;
    private String password;
    private double money = 300000;
    private List<Holding> stocksHeld = new ArrayList<>(List.of(
        new Holding("Avionics", 0), new Holding("Nuvanta Financial Core", 0), 
        new Holding("Vortex Petrochemicals", 0), new Holding("Saffro & Steam",0),
        new Holding("Gravion Motors", 0), new Holding("Maison Nouvelle", 0), 
        new Holding("Veloura Gems", 0), new Holding("Noirvelle Cosmetics", 0),
        new Holding("Skyvale Motionworks", 0), new Holding("Theravax Biogenics", 0), 
        new Holding("Noirvelle Cosmetics", 0), new Holding("Altanova Air", 0), 
        new Holding("Novabrix Developments", 0), 
        new Holding("Axaphase Chemicals", 0), new Holding("Nexus Transit Systems", 0),
        new Holding("Greenleaf Provisions", 0), new Holding("Vanguard Oil & Gas", 0), 
        new Holding("Everstead Realty Group", 0), new Holding("Cybraxo Technologies", 0),
        new Holding("Chrono Motors", 0), new Holding("Ascend air", 0)            
    ));     
    private List<Transaction> transactions = new ArrayList<>(); 


    public List<Transaction> getTransactions(){
        return transactions;
    }

    public double getMoney(){
        return money;
    }

    public void setMoney(double money){
        this.money = money;
    }

    public List<Holding> getStocksHeld(){
        return stocksHeld; 
    }
}
