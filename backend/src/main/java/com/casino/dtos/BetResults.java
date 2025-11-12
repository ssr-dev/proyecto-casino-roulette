package com.casino.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BetResults {
    private double totalBetAmount;
    private double totalWinning;
}
