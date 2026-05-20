package com.example.stock_backend.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.stock_backend.model.Company;
import com.example.stock_backend.repository.CompanyRepository;

@Service
public class MarketService {

    @Autowired CompanyRepository cr;

    private final Random random = new Random();

    @Scheduled(fixedRate = 30000)
    public void updatePrices(){

        List<Company> companies = cr.findAll();

        for(Company c : companies){
            boolean fluctuate = random.nextBoolean();

            if(fluctuate){
                boolean direction = random.nextBoolean();

                if(direction){
                    double change = random.nextFloat() * 3.13;
                    c.setPrice(c.getPrice() + change);
                    c.setChange(change);
                    c.setDirection(1);
                }
                else{
                    double change = random.nextFloat() * 3.13;
                    if(c.getPrice() >= 9.3){
                        c.setPrice(c.getPrice() - change);
                        c.setChange(change);
                        c.setDirection(-1);
                    }
                }
            }
            else{
                c.setDirection(0);
                c.setChange(0);
            }
        }
        cr.saveAll(companies);
    }
}
