package com.dashboard.finance.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.
        UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.
        SecurityContextHolder;

import org.springframework.security.core.authority.
        SimpleGrantedAuthority;

import org.springframework.security.web.authentication.
        WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.
        OncePerRequestFilter;

import com.dashboard.finance.model.User;
import com.dashboard.finance.model.Role;
import com.dashboard.finance.repository.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)

            throws ServletException, IOException {

        String authHeader =
                request.getHeader("Authorization");

        String token = null;
        String username = null;

        // Step 1 — Extract token

        if (authHeader != null &&
                authHeader.startsWith("Bearer ")) {

            token =
                    authHeader.substring(7);

            username =
                    jwtUtil.extractUsername(token);
        }

        // Step 2 — Authenticate user

        if (username != null &&
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        == null) {

            User user =
                    userRepository
                            .findByUsername(username)
                            .orElse(null);

            if (user != null &&
                    jwtUtil.validateToken(token)) {

                // Step 3 — Convert roles to authorities

                List<SimpleGrantedAuthority> authorities =
                        new ArrayList<>();

                for (Role role : user.getRoles()) {

                    authorities.add(
                            new SimpleGrantedAuthority(
                                    role.getName().name()));

                }

                // Step 4 — Create authentication token

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                authorities);

                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request));

                // Step 5 — Set authentication

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}