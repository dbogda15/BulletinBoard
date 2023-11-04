package com.example.bulletinboard.service;

import com.example.bulletinboard.dto.user.Register;
import com.example.bulletinboard.dto.user.UpdateUser;
import com.example.bulletinboard.dto.user.UserDto;
import com.example.bulletinboard.entity.User;

public interface UserMapper {
    User toUser(Register register);
    User updateUser(User user, UpdateUser updateUser);
    UserDto toUserDto(User user);
}
