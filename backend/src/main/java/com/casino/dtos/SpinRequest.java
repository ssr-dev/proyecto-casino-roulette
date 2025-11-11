package com.casino.dtos;

import lombok.Data;

@Data
public class SpinRequest {
    private Long userId;
    private String type; // "number", "color", "parity", etc.
    private String value; // "red", "17", "odd", "list12", etc.
    private double amount;
}