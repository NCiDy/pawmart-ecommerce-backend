package com.pawmart.DTO.auth;

import lombok.Data;

@Data
public class MeResponse {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String role;
}
