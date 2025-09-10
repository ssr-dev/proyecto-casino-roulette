package com.example.rule.dtos;

import java.util.List;

import com.example.rule.Model.Bet;
import com.example.rule.Model.User;

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
