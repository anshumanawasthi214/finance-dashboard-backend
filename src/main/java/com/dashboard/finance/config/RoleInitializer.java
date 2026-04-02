package com.dashboard.finance.config;

import com.dashboard.finance.model.Role;
import com.dashboard.finance.model.enums.RoleName;
import com.dashboard.finance.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleInitializer implements CommandLineRunner {

    private RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {

        RoleName[] roles = RoleName.values();

        for (int i = 0; i < roles.length; i++) {

            RoleName roleName = roles[i];

            Role existingRole =
                    roleRepository
                    .findByName(roleName)
                    .orElse(null);

            if (existingRole == null) {

                Role role = new Role();
                role.setName(roleName);

                roleRepository.save(role);

            }
        }
    }
}