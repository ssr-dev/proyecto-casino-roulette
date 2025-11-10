package com.casino.controller;

import org.springframework.web.bind.annotation.*;

import com.casino.dtos.SpinRequest;
import com.casino.dtos.SpinResult;
import com.casino.service.RouletteService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@RestController
@RequestMapping("/roulette")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class RouletteController {

    private final RouletteService rouletteService;

    @PostMapping("/spin")
    public SpinResult spinWithBet(@RequestBody SpinRequest request) {
        return rouletteService.spinWithBet(request);
    }

    @GetMapping("/last")
    public Map<String, Object> last() {
        return rouletteService.getLastSpins();
    }

    @GetMapping("/history")
    public List<Integer> history() {
        return rouletteService.getHistory();
    }

    @GetMapping("/stats/hot")
    public List<Entry<Integer, Long>> hot(@RequestParam(defaultValue = "5") int top) {
        return rouletteService.getHotNumbers(top);
    }

    @GetMapping("/stats/cold")
    public List<Entry<Integer, Long>> cold(@RequestParam(defaultValue = "5") int top) {
        return rouletteService.getColdNumbers(top);
    }
}
