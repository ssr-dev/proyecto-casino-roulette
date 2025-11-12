package com.casino.dtos;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class RoundResultDto {
    private int winningNumber;
    private LocalDateTime roundDate;
    private List<BetResponseDto> bets;
    private double totalBetAmount;
    private double totalPayout;
}