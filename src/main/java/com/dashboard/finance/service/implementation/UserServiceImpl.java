package com.dashboard.finance.service.implementation;

import com.dashboard.finance.dto.CreateUserRequest;
import com.dashboard.finance.model.Role;
import com.dashboard.finance.model.User;
import com.dashboard.finance.model.enums.RoleName;
import com.dashboard.finance.model.enums.UserStatus;
import com.dashboard.finance.repository.RoleRepository;
import com.dashboard.finance.repository.UserRepository;
import com.dashboard.finance.service.interfaces.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User createUser(CreateUserRequest request) {

        // Step 1 — Create empty role set
        Set<Role> roleSet = new HashSet<>();

        // Step 2 — Loop through role names
        Set<RoleName> roleNames = request.getRoles();

        if (roleNames != null) {

            for (RoleName roleName : roleNames) {

                Role role =
                        roleRepository
                        .findByName(roleName)
                        .orElse(null);

                if (role != null) {

                    roleSet.add(role);

                }
            }
        }

        // Step 3 — Create user object
        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        user.setStatus(UserStatus.ACTIVE);

        user.setRoles(roleSet);

        user.setCreatedAt(LocalDateTime.now());

        // Step 4 — Save user
        return userRepository.save(user);
    }
}