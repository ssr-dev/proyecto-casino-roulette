package com.casino.dtos;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String name;
    private double initialBalance = 1000.0;
}
