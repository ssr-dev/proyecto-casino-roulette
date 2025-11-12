package com.casino.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.casino.dtos.UserDto;
import com.casino.model.User;
import com.casino.service.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody Map<String, Object> requestBody) {
        try {
            String name = (String) requestBody.get("name");
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("El campo 'name' es obligatorio.");
            }

            Object balanceObj = requestBody.get("balance");
            if (balanceObj == null) {
                throw new IllegalArgumentException("El campo 'balance' es obligatorio.");
            }

            Double balance;
            if (balanceObj instanceof Number) {
                balance = ((Number) balanceObj).doubleValue();
            } else if (balanceObj instanceof String) {
                balance = Double.parseDouble((String) balanceObj);
            } else {
                throw new IllegalArgumentException("El campo 'balance' tiene un formato incorrecto.");
            }

            return userService.createUser(name, balance);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (ClassCastException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error de formato de datos en 'name' o 'balance'.");
        }
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        User user = userService.getUser(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con ID: " + id);
        }
        return user;
    }

    @GetMapping("/name/{name}")
    public UserDto getUserByName(@PathVariable String name) {
        User user = userService.getUserByName(name);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con nombre: " + name);
        }
        return UserDto.toPersonDto(user);
    }

    @GetMapping("/test")
    public String test() {
        return "Backend funcionando";
    }
}