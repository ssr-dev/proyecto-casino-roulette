package com.casino.controller;

import com.casino.model.User;
import com.casino.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestParam String name, @RequestParam double initialBalance) {
        return userService.createUser(name, initialBalance);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/balance")
    public double getBalance(@PathVariable Long id) {
        return userService.getUserById(id).getBalance();
    }

    @PostMapping("/{id}/balance")
    public User updateBalance(@PathVariable Long id, @RequestParam double amount) {
        userService.updateUserBalance(id, amount);
        return userService.getUserById(id);
    }
}