package com.example.rule.Model;

import lombok.Data;

@Data
public class Bet {
    private Long id;
    private double monto;
    private String tipo; // "numero", "color", "par/impar"
    private String valor; // Ejemplo: "17", "rojo", "par"
    private boolean ganada;
    private User usuario;

    public void calculateEarn(){
        
    }
}
