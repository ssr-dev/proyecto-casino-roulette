package com.casino.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roulettes")
@Data
public class Roulette {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private boolean active = true;
    
    @OneToMany(mappedBy = "roulette", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Round> rounds = new ArrayList<>();
    
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    public Round createNewRound() {
        Round round = new Round();
        rounds.add(round);
        return round;
    }
    
    public Round getCurrentRound() {
        return rounds.isEmpty() ? null : rounds.get(rounds.size() - 1);
    }
}