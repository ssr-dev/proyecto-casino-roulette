package com.example.rule.Model;

import lombok.Data;
import java.util.List;

@Data
public class Rulet {

    private Long id;
    private boolean activa; // true si está abierta a apuestas
    private List<Round> rondas;
    
}
