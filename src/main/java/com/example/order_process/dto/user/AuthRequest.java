package com.example.order_process.dto.user;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
