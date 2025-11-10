package com.casino.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SpinResult {
    private boolean spin;
    private int winningNumber;
}
