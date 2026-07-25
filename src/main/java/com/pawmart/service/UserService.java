package com.pawmart.service;

import com.pawmart.DTO.auth.*;
import com.pawmart.entity.User;

import java.util.Optional;

public interface UserService {
    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    MeResponse getCurrentUser();
}
