package com.example.rule.Model;

import java.util.List;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private double balance;
    private List<Bet> bets;
}
