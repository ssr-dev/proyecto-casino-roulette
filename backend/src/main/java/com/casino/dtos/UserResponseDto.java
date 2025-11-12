package com.casino.dtos;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String name;
    private double balance;
    private int totalBets;
    private int wins;
    private int losses;
}