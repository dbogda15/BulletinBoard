package com.example.bulletinboard.service.impl;

import com.example.bulletinboard.entity.User;
import com.example.bulletinboard.security.AuthUserDto;
import com.example.bulletinboard.service.AuthUserMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthUserMapperImpl implements AuthUserMapper {
    @Override
    public AuthUserDto toAuthUser(User user) {
        return AuthUserDto.builder()
                .id(user.getId())
                .role(user.getRole())
                .username(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
