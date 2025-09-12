package com.casino.dto;

import lombok.Data;

@Data
public class BetRequestDto {
    private Long userId;
    private String betType;
    private String betValue;
    private double amount;
}