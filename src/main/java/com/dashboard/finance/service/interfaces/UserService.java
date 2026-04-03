package com.dashboard.finance.service.interfaces;

import java.util.List;

import com.dashboard.finance.dto.CreateUserRequest;
import com.dashboard.finance.model.User;

public interface UserService {

    User createUser(CreateUserRequest request);
    
    List<User> getAllUsers();
    
    User updateUserStatus(Long userId, String status);
    
    User getUserById(Long id);
}