package com.dashboard.finance.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dashboard.finance.dto.CreateUserRequest;
import com.dashboard.finance.model.User;
import com.dashboard.finance.service.interfaces.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;

    }

    @PostMapping
    public User createUser(
            @Valid
            @RequestBody CreateUserRequest request) {

        return userService.createUser(request);

    }
    
    @GetMapping
    public List<User> getAllUsers() {

        return userService.getAllUsers();

    }
    
    @PutMapping("/{id}/status")
    public User updateUserStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return userService.updateUserStatus(id, status);

    }
    
    @GetMapping("/{id}")
    public User getUserById(
            @PathVariable Long id) {

        return userService.getUserById(id);

    }
}