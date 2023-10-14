package com.example.bulletinboard.security;

import com.example.bulletinboard.dto.user.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthUserDto {
    private int id;
    private Role role;
    private String username;
    private String password;
}
