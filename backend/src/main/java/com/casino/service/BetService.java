package com.casino.service;

import com.casino.model.Bet;
import com.casino.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BetService {
    @Autowired
    private BetHistoryService betHistoryService;

    public Bet placeNumberBet(User user, int number, double amount) {
        Bet bet = user.placeNumberBet(number, amount);
        betHistoryService.addBet(bet);
        return bet;
    }

    public Bet placeColorBet(User user, String color, double amount) {
        Bet bet = user.placeColorBet(color, amount);
        betHistoryService.addBet(bet);
        return bet;
    }

    public Bet placeTercioBet(User user, int tercio, double amount) {
        Bet bet = user.placeTercioBet(tercio, amount);
        betHistoryService.addBet(bet);
        return bet;
    }
}