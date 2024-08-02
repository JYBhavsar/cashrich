package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.CoinData;
import com.example.service.CoinDataService;

@RestController
@RequestMapping("/api")
public class CoinDataController {
 
    @Autowired
    private CoinDataService coinDataService;
 
    @GetMapping("/coin-data")
    public CoinData getCoinData(@RequestParam Long userId, @RequestParam String symbols) {
    	System.out.println("userId----::"+userId);
    	System.out.println("symbols----::"+symbols);
        return coinDataService.getCoinData(userId, symbols);
    }
    
}
