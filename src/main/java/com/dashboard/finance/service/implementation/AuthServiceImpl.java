package com.dashboard.finance.service.implementation;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dashboard.finance.dto.LoginRequest;
import com.dashboard.finance.dto.LoginResponse;
import com.dashboard.finance.exception.ResourceNotFoundException;
import com.dashboard.finance.model.User;
import com.dashboard.finance.repository.UserRepository;
import com.dashboard.finance.security.JwtUtil;
import com.dashboard.finance.service.interfaces.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        // Step 1 — Find user

        User user =
                userRepository
                        .findByUsername(request.getUsername())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found with username: "
                                                + request.getUsername()));

        // Step 2 — Check password

        boolean passwordMatches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword());

        if (!passwordMatches) {

            throw new ResourceNotFoundException(
                    "Invalid username or password");

        }

        // Step 3 — Generate JWT token

        String token =
                jwtUtil.generateToken(
                        user.getUsername());

        // Step 4 — Return token

        return new LoginResponse(token);
    }
}