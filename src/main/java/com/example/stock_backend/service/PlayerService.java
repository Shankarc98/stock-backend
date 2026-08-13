package com.example.stock_backend.service;

import java.util.List; 

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.example.stock_backend.model.Player;
import com.example.stock_backend.repository.PlayerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service 
public class PlayerService {
    
    private final PlayerRepository pr; 

    private final PasswordEncoder passwordEncoder; 

    PlayerService(PlayerRepository pr, PasswordEncoder passwordEncoder){
        this.pr = pr;
        this.passwordEncoder = passwordEncoder;
    }
    
    public Player createPlayer(Player p){
        
        if(pr.existsByName(p.getName())){
            throw new RuntimeException("Player already exists");
        }

        p.setPassword(passwordEncoder.encode(p.getPassword()));

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
