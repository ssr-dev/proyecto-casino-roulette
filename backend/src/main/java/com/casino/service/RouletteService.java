package com.casino.service;

import com.casino.model.Round;
import com.casino.model.Roulette;
import com.casino.repository.RouletteRepository;
import com.casino.repository.RoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Random;

@Service
@Transactional
public class RouletteService {
    
    @Autowired
    private RouletteRepository rouletteRepository;
    
    @Autowired
    private RoundRepository roundRepository;
    
    @Autowired
    private BetService betService;
    
    private final Random random = new Random();
    
    public int spinRoulette() {
        Roulette roulette = rouletteRepository.findByActive(true)
                .orElseGet(() -> {
                    Roulette newRoulette = new Roulette();
                    newRoulette.setActive(true);
                    return rouletteRepository.save(newRoulette);
                });
        
        Round round = new Round();
        round.setRoulette(roulette);
        round.spinRoulette();
        round = roundRepository.save(round);
        
        // Resolver apuestas del round anterior si existe
        resolvePreviousRoundBets(roulette);
        
        roulette.getRounds().add(round);
        rouletteRepository.save(roulette);
        
        return round.getWinningNumber();
    }
    
    private void resolvePreviousRoundBets(Roulette roulette) {
        if (roulette.getRounds().size() > 1) {
            Round previousRound = roulette.getRounds().get(roulette.getRounds().size() - 2);
            betService.resolveBets(previousRound);
        }
    }
    
    public Round getCurrentRound() {
        return rouletteRepository.findByActive(true)
                .map(Roulette::getCurrentRound)
                .orElse(null);
    }
}