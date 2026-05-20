package com.example.stock_backend.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.stock_backend.model.Player;

import java.util.Optional; 

public interface PlayerRepository extends MongoRepository<Player, String>{
    Optional<Player> findByName(String name); 
}
