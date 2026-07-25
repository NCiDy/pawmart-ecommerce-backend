package com.pawmart.service.impl;

import com.pawmart.DTO.auth.*;
import com.pawmart.entity.Role;
import com.pawmart.entity.User;
import com.pawmart.exception.AppException;
import com.pawmart.repository.RoleRepository;
import com.pawmart.repository.UserRepository;
import com.pawmart.security.JwtService;
import com.pawmart.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public RegisterResponse register(RegisterRequest request){
        if(!request.getPassword().equals(request.getConfirmPassword())){
            throw new AppException(HttpStatus.BAD_REQUEST,"Passwords and Confirm Password do not match");
        }
        if(userRepository.existsByEmail(request.getEmail())){
            throw new AppException(HttpStatus.CONFLICT,"Email already exists");
        }

        Role role = roleRepository.findByRoleName("USER")
                .orElseThrow(()-> new AppException(HttpStatus.NOT_FOUND,"Role not found"));

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setRole(role);
        user.setStatus("ACTIVE");

        User savedUser = userRepository.save(user);

        RegisterResponse response = new RegisterResponse();

        response.setId(savedUser.getId());
        response.setFullName(savedUser.getFullName());
        response.setEmail(savedUser.getEmail());
        response.setPhone(savedUser.getPhone());

        return response;
    }

    @Override
    public LoginResponse login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new AppException(HttpStatus.UNAUTHORIZED,"Email is incorrect"));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new AppException(HttpStatus.UNAUTHORIZED,"Passwords is incorrect");
        }

        String token = jwtService.generateToken(user.getEmail());

        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setId(user.getId());
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().getRoleName());

        return response;
    }

    @Override
    public MeResponse getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"User not found"));

        MeResponse response = new MeResponse();

        response.setId(user.getId());
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole().getRoleName());

        return response;
    }
}

