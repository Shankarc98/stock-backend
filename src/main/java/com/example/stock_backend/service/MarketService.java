package com.example.stock_backend.service;

import java.util.List;
import java.util.Random;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.stock_backend.model.Company;
import com.example.stock_backend.model.UpdateTime;
import com.example.stock_backend.repository.CompanyRepository;
import com.example.stock_backend.repository.TimeRepository;

import jakarta.annotation.PostConstruct;
@Service
public class MarketService {

    private final CompanyRepository cr;
    private final TimeRepository tr;

    public MarketService(CompanyRepository cr, TimeRepository tr){
        this.cr = cr; 
        this.tr = tr; 
    }
    private final Random random = new Random();

    @PostConstruct
    public void init() {
        long nextUpdateTime = System.currentTimeMillis() + 1200000;
        
        UpdateTime ut = tr.findById("timeId").orElse(new UpdateTime()); 

        ut.setNextUpdateTime(nextUpdateTime);

        tr.save(ut); 
    }

    @Scheduled(fixedRate = 1200000)
    public void updatePrices(){

        long nextUpdateTime = System.currentTimeMillis() + 1200000; 

        UpdateTime ut = tr.findById("timeId").orElse(new UpdateTime()); 

        ut.setNextUpdateTime(nextUpdateTime);

        tr.save(ut); 

        List<Company> companies = cr.findAll();

        for(Company c : companies){
            boolean fluctuate = random.nextBoolean();

            if(fluctuate){
                boolean direction = random.nextBoolean();

                if(direction){
                    double change = random.nextFloat() * 2.13;
                    c.setPrice(c.getPrice() + change);
                    c.setChange(change);
                    c.setDirection(1);
                }
                else{
                    double change = random.nextFloat() * 2.13;
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
