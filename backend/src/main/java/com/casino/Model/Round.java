package com.example.rule.Model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import lombok.Data;

@Data
public class Round {
    // private Long id;
    // private LocalDateTime date;
    private Integer winnigNumber;
    private List<Bet> bets;

    public void girarRuleta() {
        Random random = new Random();
        this.winnigNumber = random.nextInt(37); // 0 a 36
        // this.date = LocalDateTime.now();
    }

    public void getAllBets(){
        
    }
}
