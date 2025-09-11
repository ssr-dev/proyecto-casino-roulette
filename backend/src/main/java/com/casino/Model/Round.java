package com.casino.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Data;

@Data
public class Round {

    private final List<Bet> bets = new ArrayList<>();
    private int winningNumber = -1;


    public void addBet(Bet bet) {
        bets.add(bet);
    }

    public void resolve(int winningNumber) {
        this.winningNumber = winningNumber;
        for (Bet bet : bets) {
            double payout = bet.calculatePayout(winningNumber);
            if (payout > 0) {
                bet.getUser().updateBalance(payout);
            }
        }
    }
}
