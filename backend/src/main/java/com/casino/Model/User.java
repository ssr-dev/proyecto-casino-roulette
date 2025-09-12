package com.casino.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    @Column(nullable = false)
    private double balance = 1000.00;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Bet> bets = new ArrayList<>();
    
    private void validateBalance(double amount) {
        if (amount > balance) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
    }
    
    public void updateBalance(double amount) {
        this.balance += amount;
    }
}