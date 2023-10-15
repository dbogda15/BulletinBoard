package com.example.bulletinboard.service.impl;

import com.example.bulletinboard.dto.user.Register;
import com.example.bulletinboard.dto.user.UpdateUser;
import com.example.bulletinboard.dto.user.UserDto;
import com.example.bulletinboard.entity.User;
import com.example.bulletinboard.service.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapperImpl implements UserMapper {
    private final PasswordEncoder encoder;

    @Override
    public User toUser(Register register) {
        return User.builder()
                .phone(register.getPhone())
                .email(register.getUsername())
                .role(register.getRole())
                .firstName(register.getFirstName())
                .lastName(register.getLastName())
                .password(encoder.encode(register.getPassword()))
                .build();
    }

    @Override
    public User updateUser(User user, UpdateUser updateUser) {
        user.setPhone(updateUser.getPhone());
        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());
        return user;
    }

    @Override
    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .phone(user.getPhone())
                .email(user.getEmail())
                .role(user.getRole())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .image(user.getImage())
                .build();
    }
}
