package com.casino.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SpinResult {
    private int winningNumber;
    private boolean spin;

}