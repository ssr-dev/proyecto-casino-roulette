package com.casino.Model;

import java.util.List;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private double balance;
    private List<Bet> bets;

    public Bet placeBet(int number, double amount) {
        if (amount > balance) {
            throw new IllegalArgumentException("Saldo insuficiente para apostar.");
        }
        this.balance -= amount;
        return new Bet(this, number, amount);
    }

    public void updateBalance(double amount) {
        this.balance += amount;
    }
}
