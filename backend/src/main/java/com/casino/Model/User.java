package com.casino.Model;

import java.util.List;

import lombok.Data;
import lombok.Setter;

@Data
public class User {
    private Long id;
    private String name;
    private double balance;
    private List<Bet> bets;

    private void validateBalance(double amount) {
        if (amount > balance) {
            throw new IllegalArgumentException("Saldo insuficiente para apostar.");
        }
        this.balance -= amount;
    }

    // 🔹 Apuesta a número
    public Bet placeNumberBet(int number, double amount) {
        validateBalance(amount);
        Bet bet = new Bet(this, amount, BetType.NUMBER);
        bet.setNumber(number);
        return bet;
    }

    // 🔹 Apuesta a color
    public Bet placeColorBet(String color, double amount) {
        validateBalance(amount);
        Bet bet = new Bet(this, amount, BetType.COLOR);
        bet.setColor(color.toUpperCase());
        return bet;
    }

    // 🔹 Apuesta a tercio
    public Bet placeTercioBet(int tercio, double amount) {
        if (tercio < 1 || tercio > 3) {
            throw new IllegalArgumentException("El tercio debe ser 1, 2 o 3.");
        }
        validateBalance(amount);
        Bet bet = new Bet(this, amount, BetType.TERCIO);
        bet.setTercio(tercio);
        return bet;
    }

    // 🔹 Actualizar saldo después de la ronda
    public void updateBalance(double amount) {
        this.balance += amount;
    }
}
