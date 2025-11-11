package com.casino.dtos;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String name;
    private double balance;
}
