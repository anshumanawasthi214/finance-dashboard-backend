package com.dashboard.finance.service.implementation;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dashboard.finance.dto.CreateUserRequest;
import com.dashboard.finance.model.Role;
import com.dashboard.finance.model.User;
import com.dashboard.finance.model.enums.RoleName;
import com.dashboard.finance.model.enums.UserStatus;
import com.dashboard.finance.repository.RoleRepository;
import com.dashboard.finance.repository.UserRepository;
import com.dashboard.finance.service.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
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
        String encodedPassword =
                passwordEncoder.encode(
                        request.getPassword());

        user.setPassword(encodedPassword);

        user.setStatus(UserStatus.ACTIVE);

        user.setRoles(roleSet);

        user.setCreatedAt(LocalDateTime.now());

        // Step 4 — Save user
        return userRepository.save(user);
    }
    
    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();

    }
    
    @Override
    public User updateUserStatus(Long userId, String status) {

        User user =
                userRepository
                .findById(userId)
                .orElseThrow(() ->
                    new RuntimeException("User not found"));

        if (status.equalsIgnoreCase("ACTIVE")) {

            user.setStatus(UserStatus.ACTIVE);

        }
        else if (status.equalsIgnoreCase("INACTIVE")) {

            user.setStatus(UserStatus.INACTIVE);

        }

        return userRepository.save(user);

    }
    
    @Override
    public User getUserById(Long id) {

        return userRepository
                .findById(id)
                .orElseThrow(() ->
                    new RuntimeException("User not found"));

    }
}