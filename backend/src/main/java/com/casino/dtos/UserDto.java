package com.casino.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private double balance;

    public static UserDto fromUser(com.casino.model.User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setBalance(user.getBalance());
        return dto;
    }
}