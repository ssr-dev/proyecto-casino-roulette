package com.casino.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "rounds")
@Data
public class Round {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDateTime dateTime = LocalDateTime.now();
    
    private Integer winningNumber = -1;
    
    @OneToMany(mappedBy = "round", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Bet> bets = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roulette_id")
    private Roulette roulette;
    
    @Column(nullable = false)
    private boolean completed = false;
    
    private static final Random random = new Random();
    
    public void spinRoulette() {
        this.winningNumber = random.nextInt(37);
        this.dateTime = LocalDateTime.now();
    }
    
    public void resolveBets() {
        for (Bet bet : bets) {
            if (bet.isWinner(winningNumber)) {
                double payout = bet.calculatePayout(winningNumber);
                bet.getUser().updateBalance(payout);
                bet.setWon(true);
                bet.setPayout(payout);
            }
            bet.setResolved(true);
        }
        this.completed = true;
    }
    
    public void addBet(Bet bet) {
        bet.setRound(this);
        bets.add(bet);
    }
}