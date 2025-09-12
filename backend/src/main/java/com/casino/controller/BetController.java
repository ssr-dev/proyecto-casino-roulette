package com.casino.controller;

import com.casino.model.Bet;
import com.casino.service.BetService;
import com.casino.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bets")
public class BetController {
    @Autowired
    private BetService betService;

    @Autowired
    private UserService userService;

    @PostMapping("/number")
    public Bet placeNumberBet(@RequestParam Long userId, 
                             @RequestParam int number,
                             @RequestParam double amount) {
        return betService.placeNumberBet(userService.getUserById(userId), number, amount);
    }

    @PostMapping("/color")
    public Bet placeColorBet(@RequestParam Long userId,
                            @RequestParam String color,
                            @RequestParam double amount) {
        return betService.placeColorBet(userService.getUserById(userId), color, amount);
    }
}