package com.dashboard.finance.service.interfaces;

import com.dashboard.finance.dto.CreateUserRequest;
import com.dashboard.finance.model.User;

public interface UserService {

    User createUser(CreateUserRequest request);

}