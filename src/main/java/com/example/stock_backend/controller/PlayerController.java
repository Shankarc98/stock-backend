package com.example.stock_backend.controller;

import java.util.List;
import java.util.UUID; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.stock_backend.dto.MoneyRequest;
import com.example.stock_backend.model.Player;
import com.example.stock_backend.model.Transaction;
import com.example.stock_backend.repository.PlayerRepository;
import com.example.stock_backend.service.PlayerService;

@RestController
public class PlayerController {         

    @Autowired 
    private PlayerService ps; 

    @Autowired
    private PlayerRepository pr; 

    @PostMapping("/player")
    public ResponseEntity<Player> createPlayer(@RequestBody Player p){
         
        Player newPlayer = ps.createPlayer(p);

        return ResponseEntity.ok(newPlayer); 
    }
    
    @GetMapping("/player")
    public List<Player> getAllPlayers(){
        return ps.getAllPlayers(); 
    }
    
    @GetMapping("player/{name}/name")
    public ResponseEntity<Player> getByName(@PathVariable String name){
        Player p = ps.getByName(name); 

        return ResponseEntity.ok(p);
    }
    @PutMapping("/player/{id}/money")
    public Player updateMoney(@PathVariable String id, @RequestBody MoneyRequest request){
        return ps.updateMoney(id, request.getMoney()); 
    }
    
    @PatchMapping("/{id}/transaction")
    public ResponseEntity<Player> addTransaction(@PathVariable String id, @RequestBody Transaction t){

        Player p = pr.findById(id).orElseThrow();

        t.setId(UUID.randomUUID().toString());
        p.getTransactions().add(t);
        
        return ResponseEntity.ok(pr.save(p));
    }

    @PatchMapping("/{id}/deleteTransaction/{transactionId}")
    public ResponseEntity<Player> deleteTransaction(@PathVariable String id, @PathVariable String transactionId){

        Player p = pr.findById(id).orElseThrow();

        p.getTransactions().removeIf(t -> t.getId().equals(transactionId));
        return ResponseEntity.ok(pr.save(p));
    }

    @DeleteMapping("/player")
    public void deleteAll(){
        ps.deleteAll();
    }
        
}
