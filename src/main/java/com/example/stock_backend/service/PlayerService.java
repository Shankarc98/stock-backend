package com.example.stock_backend.service;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.stock_backend.model.Player;
import com.example.stock_backend.repository.PlayerRepository;

@Service 
public class PlayerService {
    
    @Autowired
    private PlayerRepository pr; 

    public Player createPlayer(Player p){
        return pr.save(p);
    }
    
    public List<Player> getAllPlayers(){
        return pr.findAll();
    }

    public Player getByName(String name){

        return pr.findByName(name).orElseThrow(() -> new RuntimeException("player not found")); 
        
    }

    public Player updateMoney(String id, double money){
        Player p = pr.findById(id).orElse(null); 

        double currentMoney = p.getMoney();

        if(currentMoney >= money) p.setMoney(currentMoney + money);

        return pr.save(p);
    }

       
    public void deleteAll(){
        pr.deleteAll(); 
    }

}
