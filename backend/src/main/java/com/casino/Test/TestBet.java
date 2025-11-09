package com.casino.Test;

import com.casino.Model.*;

public class TestBet {
    public static void main(String[] args) {
        User user = new User("Carlos", 1000);

        System.out.println("=== PRUEBA DE TODAS LAS APUESTAS DE RULETA ===\n");
        
        int winningNumber = 31; // Número ganador para las pruebas
        System.out.println("Número ganador de la ronda: " + winningNumber + "\n");

        // 🔹 1. Apuesta a NÚMERO (35:1)
        System.out.println("1. APUESTA A NÚMERO DIRECT0 (35:1)");
        Bet bet1 = user.placeNumberBet(17, 100);
        System.out.println("Apuesta: número " + bet1.getNumber());
        System.out.println("¿Ganó? " + bet1.isWinner(winningNumber));
        System.out.println("Pago: $" + bet1.calculatePayout(winningNumber));
        System.out.println("-----");

        // 🔹 2. Apuesta a COLOR (1:1)
        System.out.println("2. APUESTA A COLOR (1:1)");
        Bet bet2 = user.placeColorBet("ROJO", 100);
        System.out.println("Apuesta: color " + bet2.getColor());
        System.out.println("¿Ganó? " + bet2.isWinner(winningNumber));
        System.out.println("Pago: $" + bet2.calculatePayout(winningNumber));
        System.out.println("-----");

        // 🔹 3. Apuesta a TERCIO/DOCENA (2:1)
        System.out.println("3. APUESTA A TERCIO/DOCENA (2:1)");
        Bet bet3 = user.placeTercioBet(2, 100);
        System.out.println("Apuesta: tercio " + bet3.getTercio());
        System.out.println("¿Ganó? " + bet3.isWinner(winningNumber));
        System.out.println("Pago: $" + bet3.calculatePayout(winningNumber));
        System.out.println("-----");

        // 🔹 4. Apuesta a COLUMNA (2:1)
        System.out.println("4. APUESTA A COLUMNA (2:1)");
        Bet bet4 = user.placeColumnaBet(2, 100);
        System.out.println("Apuesta: columna " + bet4.getColumna());
        System.out.println("¿Ganó? " + bet4.isWinner(winningNumber));
        System.out.println("Pago: $" + bet4.calculatePayout(winningNumber));
        System.out.println("-----");

        // 🔹 5. Apuesta a PAR/IMPAR (1:1)
        System.out.println("5. APUESTA A PAR/IMPAR (1:1)");
        Bet bet5 = user.placeParImparBet("IMPAR", 100);
        System.out.println("Apuesta: " + bet5.getParImpar());
        System.out.println("¿Ganó? " + bet5.isWinner(winningNumber));
        System.out.println("Pago: $" + bet5.calculatePayout(winningNumber));
        System.out.println("-----");

        // 🔹 6. Apuesta a ALTO/BAJO (1:1)
        System.out.println("6. APUESTA A ALTO/BAJO (1:1)");
        Bet bet6 = user.placeAltoBajoBet("BAJO", 100);
        System.out.println("Apuesta: " + bet6.getAltoBajo());
        System.out.println("¿Ganó? " + bet6.isWinner(winningNumber));
        System.out.println("Pago: $" + bet6.calculatePayout(winningNumber));
        System.out.println("-----");

        // 🔹 7. Apuesta DOBLE (17:1)
        System.out.println("7. APUESTA DOBLE (17:1)");
        Bet bet7 = user.placeDobleBet(16, 17, 100);
        System.out.println("Apuesta: números " + bet7.getNumerosDoble()[0] + " y " + bet7.getNumerosDoble()[1]);
        System.out.println("¿Ganó? " + bet7.isWinner(winningNumber));
        System.out.println("Pago: $" + bet7.calculatePayout(winningNumber));
        System.out.println("-----");

        // 🔹 8. Apuesta CALLE (11:1)
        System.out.println("8. APUESTA CALLE (11:1)");
        Bet bet8 = user.placeCalleBet(16, 100);
        System.out.println("Apuesta: calle " + bet8.getCalle() + "-" + (bet8.getCalle()+1) + "-" + (bet8.getCalle()+2));
        System.out.println("¿Ganó? " + bet8.isWinner(winningNumber));
        System.out.println("Pago: $" + bet8.calculatePayout(winningNumber));
        System.out.println("-----");

        // 🔹 Prueba con número perdedor
        System.out.println("=== PRUEBA CON NÚMERO PERDEDOR (5) ===");
        int losingNumber = 5;
        System.out.println("Número ganador: " + losingNumber);
        System.out.println("Apuesta a número 17 - ¿Ganó? " + bet1.isWinner(losingNumber));
        System.out.println("Apuesta a color ROJO - ¿Ganó? " + bet2.isWinner(losingNumber));
        System.out.println("Apuesta a tercio 2 - ¿Ganó? " + bet3.isWinner(losingNumber));
        System.out.println("-----");

        // 🔹 Prueba con el número 0
        System.out.println("=== PRUEBA CON EL NÚMERO 0 ===");
        int zeroNumber = 0;
        System.out.println("Número ganador: " + zeroNumber);
        Bet betZero = user.placeNumberBet(0, 100);
        System.out.println("Apuesta a número 0 - ¿Ganó? " + betZero.isWinner(zeroNumber));
        System.out.println("Pago: $" + betZero.calculatePayout(zeroNumber));
        System.out.println("Apuesta a color ROJO - ¿Ganó? " + bet2.isWinner(zeroNumber));
        System.out.println("-----");

        // 🔹 Mostrar saldo final del usuario
        System.out.println("=== SALDO FINAL DEL USUARIO ===");
        System.out.println("Saldo inicial: $1000");
        System.out.println("Total apostado: $800");
        System.out.println("Saldo restante: $" + user.getBalance());
        
        // Calcular ganancias totales si todas ganaran
        double totalGanancias = bet1.calculatePayout(winningNumber) + 
                               bet2.calculatePayout(winningNumber) + 
                               bet3.calculatePayout(winningNumber) + 
                               bet4.calculatePayout(winningNumber) + 
                               bet5.calculatePayout(winningNumber) + 
                               bet6.calculatePayout(winningNumber) + 
                               bet7.calculatePayout(winningNumber) + 
                               bet8.calculatePayout(winningNumber);
        
        System.out.println("Ganancias potenciales si todas ganan: $" + totalGanancias);
    }
}