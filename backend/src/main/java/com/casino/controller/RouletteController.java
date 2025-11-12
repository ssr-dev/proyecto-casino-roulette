package com.casino.controller;

import org.springframework.web.bind.annotation.*;

import com.casino.dtos.SpinRequest;
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
    @GetMapping("/spin")
    public SpinResult spin() {
        System.out.println("Spin automático generado: lalalala ");
        return rouletteService.autoSpin();
    }

    @PostMapping("/bet/{userId}")
    public double placeBets(
            @PathVariable Long userId,
            @RequestBody List<SpinRequest> bets) {
        return rouletteService.placeBets(userId, bets);
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
