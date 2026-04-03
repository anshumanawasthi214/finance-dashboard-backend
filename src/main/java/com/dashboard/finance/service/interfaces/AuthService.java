package com.dashboard.finance.service.interfaces;

import com.dashboard.finance.dto.LoginRequest;
import com.dashboard.finance.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

}