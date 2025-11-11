package com.casino.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.casino.dtos.SpinResult;
import com.casino.service.BetService;
import com.casino.service.RouletteService;
import com.casino.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/roulette", produces = "application/json")
@CrossOrigin(origins = "*")
public class RouletteController {

    @Autowired
    private RouletteService rouletteService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private BetService betService;


    @PostMapping("/spin")
    public SpinResult spinAndBet(@RequestBody Map<String, Object> requestBody) {
        try {
            Object userIdObj = requestBody.get("userId");
            if (userIdObj == null) {
                throw new IllegalArgumentException("El campo 'userId' es obligatorio.");
            }
            Long userId = ((Number) userIdObj).longValue();

            String type = (String) requestBody.get("type");
            String value = (String) requestBody.get("value");
            if (type == null || value == null) {
                throw new IllegalArgumentException("Los campos 'type' y 'value' son obligatorios.");
            }
            
            Object amountObj = requestBody.get("amount");
            if (amountObj == null) {
                throw new IllegalArgumentException("El campo 'amount' es obligatorio.");
            }
            
            Double amount;
            if (amountObj instanceof Number) {
                amount = ((Number) amountObj).doubleValue();
            } else if (amountObj instanceof String) {
                amount = Double.parseDouble((String) amountObj);
            } else {
                throw new IllegalArgumentException("El campo 'amount' tiene un formato incorrecto.");
            }

            return rouletteService.spinAndProcessBet(
                userId,
                type,
                value,
                amount
            );
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (ClassCastException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error de formato de datos: Asegúrate de que los campos numéricos sean números y las cadenas sean texto.");
        }
    }

    @GetMapping("/autospin")
    public SpinResult autoSpinTrigger() {
        return rouletteService.autoSpin();
    }
    
    @GetMapping("/bet/{userId}")
    public void placeBet(
            @PathVariable Long userId,
            @RequestParam List<String> type,
            @RequestParam List<String> value,
            @RequestParam List<Double> amount) {

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