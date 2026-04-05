package com.dashboard.finance.config;

import com.dashboard.finance.model.Role;
import com.dashboard.finance.model.User;
import com.dashboard.finance.model.enums.RoleName;
import com.dashboard.finance.model.enums.UserStatus;
import com.dashboard.finance.repository.RoleRepository;
import com.dashboard.finance.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class AdminInitializer implements CommandLineRunner {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public AdminInitializer(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        // Check if admin already exists
        User existingAdmin =
                userRepository
                .findByUsername("admin")
                .orElse(null);

        if (existingAdmin == null) {

            // Get ADMIN role
            Role adminRole =
                    roleRepository
                    .findByName(RoleName.ROLE_ADMIN)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "ROLE_ADMIN not found"));

            // Create admin user
            User admin = new User();

            admin.setUsername("admin");

            admin.setEmail("admin@gmail.com");

            admin.setPassword(
                    passwordEncoder.encode("Admin@123"));

            admin.setRoles(Set.of(adminRole));

            admin.setStatus(
                    UserStatus.ACTIVE); // if you have status field

            userRepository.save(admin);

            System.out.println(
                    "Default ADMIN created successfully.");
        }
    }
}