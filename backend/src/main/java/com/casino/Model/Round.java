package com.example.rule.Model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import lombok.Data;

@Data
public class Round {
    private Long id;
    private LocalDateTime fecha;
    private Integer numeroGanador;
    private List<Bet> apuestas;

    public void girarRuleta() {
        Random random = new Random();
        this.numeroGanador = random.nextInt(37); // 0 a 36
        this.fecha = LocalDateTime.now();
    }

    public void getAllBets(){
        
    }
}
