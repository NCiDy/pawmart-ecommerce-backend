package com.pawmart.DTO.auth;


import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private Long id;
    private String fullName;
    private String email;
    private String role;
}
