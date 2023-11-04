package com.example.bulletinboard.service;

import com.example.bulletinboard.entity.User;
import com.example.bulletinboard.security.AuthUserDto;

public interface AuthUserMapper {
    AuthUserDto toAuthUser(User user);
}
