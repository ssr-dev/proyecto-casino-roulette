package com.example.rule.Model;

import java.util.List;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String nombre;
    private double saldo;
    private List<Bet> apuestas;
}
