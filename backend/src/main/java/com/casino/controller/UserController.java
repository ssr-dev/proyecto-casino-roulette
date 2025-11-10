package com.casino.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.casino.Model.User;
import com.casino.dtos.CreateUserRequest;
import com.casino.dtos.UserDto;
import com.casino.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /** Crear usuario */
    @PostMapping
    public UserDto createUser(@RequestBody CreateUserRequest request) {
       try { 
    	User user = userService.createUser(request.getName(), request.getBalance());
        return UserDto.toPersonDto(user);
    }catch (IllegalArgumentException e) {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
	}
	}

    /** Obtener usuario por ID */
    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        if (user == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        return UserDto.toPersonDto(user);
    }
    @GetMapping("/test")
public String test() {
    return "Backend funcionando";
}
}
