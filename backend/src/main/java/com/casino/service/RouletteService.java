package com.casino.service;

import com.casino.model.Round;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class RouletteService {
    private final Random random = new Random();
    private Round currentRound;

    public int spinRoulette() {
        return random.nextInt(37);
    }

    public Round getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(Round round) {
        this.currentRound = round;
    }
}