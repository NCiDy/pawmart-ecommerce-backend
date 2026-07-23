package com.pawmart.service;

import com.pawmart.DTO.auth.LoginRequest;
import com.pawmart.DTO.auth.LoginResponse;
import com.pawmart.DTO.auth.RegisterRequest;
import com.pawmart.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    User save(User user);
    User register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}
