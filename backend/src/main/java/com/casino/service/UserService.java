package com.casino.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.casino.Model.User;

@Service
public class UserService {

    private final Map<Long, User> users = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    /** Crear usuario */
    public User createUser(String name, double balance) {
        long id = idGenerator.getAndIncrement();
        User user = new User(name, balance);
        user.setId(id);
        users.put(id, user);
        return user;
    }

    /** Obtener usuario por ID */
    public User getUser(Long id) {
        return users.get(id);
    }

    /** Actualizar saldo del usuario (por si se quiere usar) */
    public void updateUser(User user) {
        users.put(user.getId(), user);
    }

    /** Verificar si existe */
    public boolean exists(Long id) {
        return users.containsKey(id);
    }
}
