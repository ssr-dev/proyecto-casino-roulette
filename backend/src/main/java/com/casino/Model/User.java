package com.casino.Model;

import java.util.List;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private double balance;
    private List<Bet> bets;

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

    // 🔹 Apuesta a columna
    public Bet placeColumnaBet(int columna, double amount) {
        if (columna < 1 || columna > 3) {
            throw new IllegalArgumentException("La columna debe ser 1, 2 o 3.");
        }
        validateBalance(amount);
        Bet bet = new Bet(this, amount, BetType.COLUMNA);
        bet.setColumna(columna);
        return bet;
    }

    // 🔹 Apuesta a par/impar
    public Bet placeParImparBet(String parImpar, double amount) {
        if (!parImpar.equalsIgnoreCase("PAR") && !parImpar.equalsIgnoreCase("IMPAR")) {
            throw new IllegalArgumentException("Debe apostar a 'PAR' o 'IMPAR'.");
        }
        validateBalance(amount);
        Bet bet = new Bet(this, amount, BetType.PAR_IMPAR);
        bet.setParImpar(parImpar.toUpperCase());
        return bet;
    }

    // 🔹 Apuesta a alto/bajo
    public Bet placeAltoBajoBet(String altoBajo, double amount) {
        if (!altoBajo.equalsIgnoreCase("ALTO") && !altoBajo.equalsIgnoreCase("BAJO")) {
            throw new IllegalArgumentException("Debe apostar a 'ALTO' (19-36) o 'BAJO' (1-18).");
        }
        validateBalance(amount);
        Bet bet = new Bet(this, amount, BetType.ALTO_BAJO);
        bet.setAltoBajo(altoBajo.toUpperCase());
        return bet;
    }

    // 🔹 Apuesta doble (dos números adyacentes)
    public Bet placeDobleBet(int num1, int num2, double amount) {
        if (num1 < 0 || num1 > 36 || num2 < 0 || num2 > 36) {
            throw new IllegalArgumentException("Los números deben estar entre 0 y 36.");
        }
        if (!sonNumerosAdyacentes(num1, num2)) {
            throw new IllegalArgumentException("Los números deben ser adyacentes en la ruleta.");
        }
        validateBalance(amount);
        Bet bet = new Bet(this, amount, BetType.DOBLE);
        bet.setNumerosDoble(new Integer[]{num1, num2});
        return bet;
    }

    // 🔹 Apuesta calle (fila de tres números consecutivos)
    public Bet placeCalleBet(int calle, double amount) {
        if (calle < 1 || calle > 34 || calle % 3 != 1) {
            throw new IllegalArgumentException("La calle debe ser un número válido: 1, 4, 7, 10, 13, 16, 19, 22, 25, 28, 31, 34.");
        }
        validateBalance(amount);
        Bet bet = new Bet(this, amount, BetType.CALLE);
        bet.setCalle(calle);
        return bet;
    }

    // 🔹 Método auxiliar para verificar si dos números son adyacentes
    private boolean sonNumerosAdyacentes(int num1, int num2) {
        // Números adyacentes horizontalmente en la tabla de ruleta
        if (Math.abs(num1 - num2) == 1) {
            // Verificar que no estén en diferentes docenas (ej: 3 y 4 sí, pero 3 y 0 no)
            int docena1 = (num1 - 1) / 12;
            int docena2 = (num2 - 1) / 12;
            return docena1 == docena2;
        }
        
        // Números adyacentes verticalmente (misma columna)
        if (Math.abs(num1 - num2) == 3) {
            return true;
        }
        
        return false;
    }

    // 🔹 Actualizar saldo después de la ronda
    public void updateBalance(double amount) {
        this.balance += amount;
    }

    // 🔹 Agregar apuesta a la lista del usuario
    public void addBet(Bet bet) {
        if (bets != null) {
            bets.add(bet);
        }
    }
}