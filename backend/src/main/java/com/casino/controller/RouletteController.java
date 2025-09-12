package com.casino.controller;

import com.casino.service.RouletteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roulette")
public class RouletteController {
    @Autowired
    private RouletteService rouletteService;

    @PostMapping("/spin")
    public int spin() {
        return rouletteService.spinRoulette();
    }

    @GetMapping("/current-round")
    public Object getCurrentRound() {
        return rouletteService.getCurrentRound();
    }
}