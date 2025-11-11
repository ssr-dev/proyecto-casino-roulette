package com.casino.model;

import lombok.Data;

@Data
public class User {

    private Long id;
    private String name;
    private double balance;

    public User(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    private void validateBalance(double amount) {
        if (amount > balance) {
            throw new IllegalArgumentException("Saldo insuficiente para apostar.");
        }
        this.balance -= amount;
    }

    public Bet placeNumberBet(int number, double amount) {
        validateBalance(amount);
        Bet bet = new Bet(this, amount, BetType.NUMBER);
        bet.setNumber(number);
        return bet;
    }

    public Bet placeColorBet(String color, double amount) {
        validateBalance(amount);
        Bet bet = new Bet(this, amount, BetType.COLOR);
        bet.setColor(color.toUpperCase());
        return bet;
    }

    public Bet placeTercioBet(int tercio, double amount) {
        if (tercio < 1 || tercio > 3) {
            throw new IllegalArgumentException("El tercio debe ser 1, 2 o 3.");
        }
        validateBalance(amount);
        Bet bet = new Bet(this, amount, BetType.DOZEN);
        bet.setTercio(tercio);
        return bet;
    }

    public Bet placeColumnaBet(int columna, double amount) {
        if (columna < 1 || columna > 3) {
            throw new IllegalArgumentException("La columna debe ser 1, 2 o 3.");
        }
        validateBalance(amount);
        Bet bet = new Bet(this, amount, BetType.COLUMN);
        bet.setColumna(columna);
        return bet;
    }

    public Bet placeParImparBet(String parImpar, double amount) {
        if (!parImpar.equalsIgnoreCase("PAR") && !parImpar.equalsIgnoreCase("IMPAR")) {
            throw new IllegalArgumentException("Debe apostar a 'PAR' o 'IMPAR'.");
        }
        validateBalance(amount);
        Bet bet = new Bet(this, amount, BetType.PARITY);
        bet.setParImpar(parImpar.toUpperCase());
        return bet;
    }

    public Bet placeAltoBajoBet(String altoBajo, double amount) {
        if (!altoBajo.equalsIgnoreCase("ALTO") && !altoBajo.equalsIgnoreCase("BAJO")) {
            throw new IllegalArgumentException("Debe apostar a 'ALTO' (19-36) o 'BAJO' (1-18).");
        }
        validateBalance(amount);
        Bet bet = new Bet(this, amount, BetType.RANGE);
        bet.setAltoBajo(altoBajo.toUpperCase());
        return bet;
    }

    public void updateBalance(double amount) {
        this.balance += amount;
    }

}