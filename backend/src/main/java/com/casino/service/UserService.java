package com.casino.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.casino.model.User;

@Service
public class UserService {

    private static final int MAX_USERS_CAPACITY = 20;

    private final Map<Long, User> users = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    /** Crear usuario */
    public User createUser(String name, double balance) {
        if (users.size() >= MAX_USERS_CAPACITY) {
            throw new IllegalStateException("Maximum user capacity reached.");
        }

        boolean nameExists = users.values().stream()
                .anyMatch(u -> u.getName().equalsIgnoreCase(name));
        if (nameExists) {
            throw new IllegalArgumentException("User with name '" + name + "' already exists.");
        }

        long id = idGenerator.getAndIncrement();
        User user = new User(name, balance);
        user.setId(id);
        users.put(id, user);
        return user;
    }

    public User getUser(Long id) {
        return users.get(id);
    }

    /** ✅ Obtener usuario por nombre */
    public User getUserByName(String name) {
        return users.values().stream()
                .filter(u -> u.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null); // puedes lanzar excepción si prefieres
    }


    public void updateUser(User user) {
        users.put(user.getId(), user);
    }

    public boolean exists(Long id) {
        return users.containsKey(id);
    }

    public User findById(Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }
}
