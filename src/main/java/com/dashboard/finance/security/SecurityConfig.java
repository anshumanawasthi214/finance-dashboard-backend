package com.dashboard.finance.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {


    private JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtFilter) {

        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth ->
                    auth

                    // Public APIs
                    .requestMatchers(
                            "/auth/**",
                            "/swagger-ui/**",
                            "/v3/api-docs/**",
                            "/h2-console/**")
                    .permitAll()

                    // Admin Only
                    .requestMatchers(
                            "/users/**")
                    .hasRole("ADMIN")

                    // Admin + Analyst
                    .requestMatchers(
                            "/transactions/**")
                    .hasAnyRole(
                            "ADMIN",
                            "ANALYST")

                    // All Roles
                    .requestMatchers(
                            "/dashboard/**")
                    .hasAnyRole(
                            "ADMIN",
                            "ANALYST",
                            "VIEWER")

                    .anyRequest()
                    .authenticated())


            .addFilterBefore(
                    jwtFilter,
                    UsernamePasswordAuthenticationFilter.class);

        http.headers(headers ->
                headers.frameOptions(
                        frame -> frame.disable()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }
}