package com.casino.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SpinResult {
    private int winningNumber;
    private String color;
    private double payout;
    private double newBalance;
}
