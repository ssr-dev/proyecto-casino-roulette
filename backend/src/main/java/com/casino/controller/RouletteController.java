package com.casino.controller;

import org.springframework.web.bind.annotation.*;

import com.casino.dtos.SpinResult;
import com.casino.service.RouletteService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roulette")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class RouletteController {

    private final RouletteService rouletteService;

    /** ✅ GET /roulette/spin */
    @PostMapping("/spin")
    public SpinResult spin() {
        return rouletteService.autoSpin();
    }

@GetMapping("/bet/{userId}")
public void placeBet(
        @PathVariable Long userId,
        @RequestParam List<String> type,
        @RequestParam List<String> value,
        @RequestParam List<Double> amount) {

    rouletteService.placeBet(userId, type, value, amount);
}


    @GetMapping("/history")
    public List<Integer> history() {
        return rouletteService.getHistory();
    }

    @GetMapping("/stats/hot")
    public List<Integer> hot(@RequestParam(defaultValue = "5") int top) {
        return rouletteService.getHot(top).stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @GetMapping("/stats/cold")
    public List<Integer> cold(@RequestParam(defaultValue = "5") int top) {
        return rouletteService.getCold(top).stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
