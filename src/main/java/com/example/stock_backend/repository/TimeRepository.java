package com.example.stock_backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.stock_backend.model.UpdateTime;

@Repository
public interface TimeRepository extends MongoRepository<UpdateTime, String>{
    
}
