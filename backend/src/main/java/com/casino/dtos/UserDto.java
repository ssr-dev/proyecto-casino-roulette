package com.casino.dtos;

import java.util.List;

import com.casino.Model.Bet;
import com.casino.Model.User;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private double balance;
    private List<Bet> bets;

    public static UserDto toPersonDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setBalance(user.getBalance());
        userDto.setBets(user.getBets());
        return userDto;
    }
}
