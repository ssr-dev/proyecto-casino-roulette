package com.casino.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "bets")
@Data
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private double amount;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BetType type;
    
    private Integer number;
    private String color;
    private Integer tercio;
    
    @Column(nullable = false)
    private LocalDateTime timestamp;
    
    private boolean resolved = false;
    private boolean won = false;
    private double payout = 0;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "round_id")
    private Round round;
    
    public double calculatePayout(int winningNumber) {
        if (!isWinner(winningNumber)) return 0;
        switch (type) {
            case NUMBER:
                return amount * 36;
            case COLOR:
                return amount * 2;
            case TERCIO:
                return amount * 3;
            default:
                return 0;
        }
    }
    
    public boolean isWinner(int winningNumber) {
        if (winningNumber < 0 || winningNumber > 36) return false;
        
        return switch (type) {
            case NUMBER -> number != null && number == winningNumber;
            case COLOR -> {
                if (winningNumber == 0) yield false;
                String winningColor = (winningNumber % 2 == 1) ? "ROJO" : "NEGRO";
                yield color != null && color.equalsIgnoreCase(winningColor);
            }
            case TERCIO -> {
                if (winningNumber == 0) yield false;
                int winningTercio = (winningNumber <= 12) ? 1 : (winningNumber <= 24) ? 2 : 3;
                yield tercio != null && tercio == winningTercio;
            }
        };
    }
    
    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }
}