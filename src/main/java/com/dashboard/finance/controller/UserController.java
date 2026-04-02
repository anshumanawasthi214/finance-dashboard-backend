package com.dashboard.finance.controller;

import com.dashboard.finance.dto.CreateUserRequest;
import com.dashboard.finance.model.User;
import com.dashboard.finance.service.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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
}