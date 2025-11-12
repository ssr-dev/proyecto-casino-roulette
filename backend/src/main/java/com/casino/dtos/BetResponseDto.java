package com.casino.dtos;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BetResponseDto {
    private Long id;
    private String betType;
    private String betValue;
    private double amount;
    private boolean won;
    private double payout;
    private LocalDateTime timestamp;
}