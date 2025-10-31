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
    private Integer columna;
    private String parImpar;
    private String altoBajo;
    private Integer[] numerosDoble; // Para apuesta doble (2 números)
    private Integer calle; // Para apuesta calle (número inicial de la fila)

    // 🔹 Constructor único y genérico
    public Bet(User user, double amount, BetType type) {
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

            case TERCIO:
                int tercioGanador = getTercio(winningNumber);
                return tercio != null && tercio == tercioGanador;

            case COLUMNA:
                int columnaGanadora = getColumna(winningNumber);
                return columna != null && columna == columnaGanadora;

            case PAR_IMPAR:
                String resultadoParImpar = (winningNumber % 2 == 0) ? "PAR" : "IMPAR";
                return parImpar != null && parImpar.equalsIgnoreCase(resultadoParImpar);

            case ALTO_BAJO:
                String resultadoAltoBajo = (winningNumber >= 1 && winningNumber <= 18) ? "BAJO" : "ALTO";
                return altoBajo != null && altoBajo.equalsIgnoreCase(resultadoAltoBajo);

            case DOBLE:
                if (numerosDoble == null || numerosDoble.length != 2) return false;
                return (numerosDoble[0] == winningNumber || numerosDoble[1] == winningNumber);

            case CALLE:
                if (calle == null) return false;
                return perteneceACalle(winningNumber, calle);

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
            case PAR_IMPAR:
            case ALTO_BAJO:
                return amount * 2;  // paga 1:1 + apuesta
            case TERCIO:
            case COLUMNA:
                return amount * 3;  // paga 2:1 + apuesta
            case DOBLE:
                return amount * 18; // paga 17:1 + apuesta
            case CALLE:
                return amount * 12; // paga 11:1 + apuesta
            default:
                return 0;
        }
    }

    // Determina el color de un número (simplificado para ruleta europea)
    private String getColor(int number) {
        int[] rojos = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
        for (int rojo : rojos) {
            if (number == rojo) return "ROJO";
        }
        return "NEGRO";
    }

    // Determina el tercio al que pertenece un número
    private int getTercio(int number) {
        if (number >= 1 && number <= 12) return 1;
        if (number >= 13 && number <= 24) return 2;
        if (number >= 25 && number <= 36) return 3;
        return -1;
    }

    // Determina la columna a la que pertenece un número
    private int getColumna(int number) {
        if (number % 3 == 1) return 1; // Columna 1: 1, 4, 7, 10, 13, 16, 19, 22, 25, 28, 31, 34
        if (number % 3 == 2) return 2; // Columna 2: 2, 5, 8, 11, 14, 17, 20, 23, 26, 29, 32, 35
        if (number % 3 == 0) return 3; // Columna 3: 3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36
        return -1;
    }

    // Verifica si un número pertenece a una calle específica
    private boolean perteneceACalle(int number, int calleInicial) {
        // Las calles son: 1-2-3, 4-5-6, 7-8-9, ..., 34-35-36
        return number >= calleInicial && number <= calleInicial + 2;
    }
}