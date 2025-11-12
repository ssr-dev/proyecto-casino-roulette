package com.casino.controller;

import com.casino.model.Bet;
import com.casino.model.User;
import com.casino.service.BetService;
import com.casino.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bets")
@CrossOrigin(origins = "http://localhost:3000") // Cambia el puerto si es necesario
public class BetController {
    @Autowired
    private BetService betService;

    @Autowired
    private UserService userService;

    @PostMapping("/number")
    public Bet placeNumberBet(@RequestParam Long userId, 
                             @RequestParam int number,
                             @RequestParam double amount) {
        User user = userService.getUserById(userId); // ✅ Aquí se usa la clase User
        return betService.placeNumberBet(user, number, amount);
    }

    @PostMapping("/color")
    public Bet placeColorBet(@RequestParam Long userId,
                            @RequestParam String color,
                            @RequestParam double amount) {
        User user = userService.getUserById(userId); // ✅ Aquí se usa la clase User
        return betService.placeColorBet(user, color, amount);
    }
    
    @PostMapping("/tercio")
    public Bet placeTercioBet(@RequestParam Long userId,
                         @RequestParam int tercio,
                         @RequestParam double amount) {
        User user = userService.getUserById(userId); // ✅ Aquí se usa la clase User
        return betService.placeTercioBet(user, tercio, amount);
    }
}