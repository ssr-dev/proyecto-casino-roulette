package com.casino.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserResponseDto {
    private Long id;
    private String name;
    private double balance;
    private int totalBets;
    private int wins;
    private int losses;
}