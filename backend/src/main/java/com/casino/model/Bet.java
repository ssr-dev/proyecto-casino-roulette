package com.casino.model;

import lombok.Data;

@Data
public class Bet {

    private User user;
    private double amount;
    private BetType type;


    private Integer number; 
    private String color;   
    private Integer tercio;
    private Integer columna;
    private String parImpar;
    private String altoBajo;

    public Bet(User user,double amount, BetType type) {
        this.user = user;
        this.amount = amount;
        this.type = type;
    }

    // No más constructores específicos - usaremos solo setters desde la clase User

    public boolean isWinner(int winningNumber) {
        if (winningNumber == 0) {
            // El 0 solo gana en apuestas directas al número 0
            return type == BetType.NUMBER && number != null && number == 0;
        }

        switch (type) {
            case NUMBER:
                return number != null && number == winningNumber;

            case COLOR:
                String winningColor = getColor(winningNumber);
                return color != null && color.equalsIgnoreCase(winningColor);

            case DOZEN:
                int tercioGanador = getTercio(winningNumber);
                return tercio != null && tercio == tercioGanador;

            case COLUMN:
                int columnaGanadora = getColumna(winningNumber);
                return columna != null && columna == columnaGanadora;

            case PARITY:
                String resultadoParImpar = (winningNumber % 2 == 0) ? "PAR" : "IMPAR";
                return parImpar != null && parImpar.equalsIgnoreCase(resultadoParImpar);

            case RANGE:
                String resultadoAltoBajo = (winningNumber >= 1 && winningNumber <= 18) ? "BAJO" : "ALTO";
                return altoBajo != null && altoBajo.equalsIgnoreCase(resultadoAltoBajo);

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
            case PARITY:
            case RANGE:
                return amount * 2;  // paga 1:1 + apuesta

            case DOZEN:
            case COLUMN:
                return amount * 3;  // paga 2:1 + apuesta

            default:
                return 0;
        }
    }

    private String getColor(int number) {
        int[] rojos = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
        for (int rojo : rojos) {
            if (number == rojo) return "ROJO";
        }
        return "NEGRO";
    }

    private int getTercio(int number) {
        if (number >= 1 && number <= 12) return 1;
        if (number >= 13 && number <= 24) return 2;
        if (number >= 25 && number <= 36) return 3;
        return -1;
    }

    private int getColumna(int number) {
        if (number % 3 == 1) return 1; // Columna 1: 1, 4, 7, 10, 13, 16, 19, 22, 25, 28, 31, 34
        if (number % 3 == 2) return 2; // Columna 2: 2, 5, 8, 11, 14, 17, 20, 23, 26, 29, 32, 35
        if (number % 3 == 0) return 3; // Columna 3: 3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36
        return -1;
    }

    
    
}