package com.casino.Model;

import lombok.Data;

@Data
public class Bet {
    private final User user;
    private final double amount;
    private final BetType type;

    private Integer number; 
    private String color;   
    private Integer tercio;

    
    public Bet(User user, double amount, BetType type) {
        this.user = user;
        this.amount = amount;
        this.type = type;
    }

    public boolean isWinner(int winningNumber) {
        switch (type) {
            case NUMBER:
                return number != null && number == winningNumber;

            case COLOR:
                if (winningNumber == 0) return false;
                String winningColor = getColor(winningNumber);
                return color != null && color.equalsIgnoreCase(winningColor);

            case TERCIO:
                if (winningNumber == 0) return false;
                int tercioGanador = getTercio(winningNumber);
                return tercio != null && tercio == tercioGanador;

            default:
                return false;
        }
    }

    public double calculatePayout(int winningNumber) {
        if (!isWinner(winningNumber)) return 0;

        switch (type) {
            case NUMBER:
                return amount * 36; // paga 35:1 + apuesta
            case COLOR:
                return amount * 2;  // paga 1:1 + apuesta
            case TERCIO:
                return amount * 3;  // paga 2:1 + apuesta
            default:
                return 0;
        }
    }

    // Determina el color de un número
    private String getColor(int number) {
        // Simplificación: números pares = NEGRO, impares = ROJO
        // (Se puede ajustar a la disposición real de la ruleta europea/americana)
        return (number % 2 == 0) ? "NEGRO" : "ROJO";
    }

    // Determina el tercio al que pertenece un número
    private int getTercio(int number) {
        if (number >= 1 && number <= 12) return 1;
        if (number >= 13 && number <= 24) return 2;
        if (number >= 25 && number <= 36) return 3;
        return -1; // 0 no pertenece a ningún tercio
    }
}
