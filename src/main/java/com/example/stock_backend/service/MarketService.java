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
            double previousPrice = c.getPrice();
            boolean fluctuate = random.nextBoolean();

            if(fluctuate){
                boolean direction = random.nextBoolean();

                if(direction){
                    c.setPrice(previousPrice + random.nextFloat() * 2.13);
                }
                else{
                    double decrease = random.nextFloat() * 2.13;
                    if(previousPrice >= 9.3){
                        c.setPrice(previousPrice - decrease);
                    }
                }
            }

            updateMovement(c, previousPrice);
        }
        cr.saveAll(companies);
    }

    static void updateMovement(Company company, double previousPrice){
        double change = company.getPrice() - previousPrice;
        if(change > 0){
            company.setChange(change);
            company.setDirection(1);
        } else if(change < 0){
            company.setChange(Math.abs(change));
            company.setDirection(-1);
        } else {
            company.setDirection(0);
            company.setChange(0);
        }
    }
}
