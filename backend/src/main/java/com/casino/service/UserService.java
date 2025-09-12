package com.casino.service;

import com.casino.model.User;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private final Map<Long, User> users = new HashMap<>();
    private long nextId = 1;

    public User createUser(String name, double initialBalance) {
        User user = new User();
        user.setId(nextId++);
        user.setName(name);
        user.setBalance(initialBalance);
        users.put(user.getId(), user);
        return user;
    }

    public User getUserById(Long id) {
        return users.get(id);
    }

    public void updateUserBalance(Long userId, double amount) {
        User user = users.get(userId);
        if (user != null) {
            user.setBalance(user.getBalance() + amount);
        }
    }
}