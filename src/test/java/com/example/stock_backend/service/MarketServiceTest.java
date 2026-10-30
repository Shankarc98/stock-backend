package com.example.stock_backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.example.stock_backend.model.Company;

class MarketServiceTest {

    @Test
    void recordsPositivePriceMovement(){
        Company company = new Company("Example", 102.5, 0, "");

        MarketService.updateMovement(company, 100);

        assertEquals(1, company.getDirection());
        assertEquals(2.5, company.getChange(), 0.000001);
    }

    @Test
    void recordsNegativePriceMovement(){
        Company company = new Company("Example", 97.5, 0, "");

        MarketService.updateMovement(company, 100);

        assertEquals(-1, company.getDirection());
        assertEquals(2.5, company.getChange(), 0.000001);
    }

    @Test
    void recordsUnchangedPriceMovement(){
        Company company = new Company("Example", 100, 4.5, "");

        MarketService.updateMovement(company, 100);

        assertEquals(0, company.getDirection());
        assertEquals(0, company.getChange());
    }
}
