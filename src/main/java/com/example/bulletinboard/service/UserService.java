package com.example.bulletinboard.service;

import com.example.bulletinboard.dto.user.Register;
import com.example.bulletinboard.dto.user.UpdateUser;
import com.example.bulletinboard.dto.user.UserDto;

import java.util.List;

public interface UserService {
    UserDto create(Register register);
    UserDto getById(Integer id);
    List<UserDto> getAll();
    UserDto updateUser(Integer id, UpdateUser updateUser);
}
