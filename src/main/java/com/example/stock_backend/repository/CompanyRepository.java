package com.example.stock_backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.stock_backend.model.Company;


public interface CompanyRepository extends MongoRepository<Company, String>{

}
