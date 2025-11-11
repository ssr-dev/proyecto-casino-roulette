package com.casino.dtos;

import com.casino.model.User;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private double balance;

    public static UserDto toPersonDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setBalance(user.getBalance());
        return userDto;
    }
}
