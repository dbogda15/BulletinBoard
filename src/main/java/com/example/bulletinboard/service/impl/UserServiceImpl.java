package com.example.bulletinboard.service.impl;

import com.example.bulletinboard.dto.user.Register;
import com.example.bulletinboard.dto.user.UpdateUser;
import com.example.bulletinboard.dto.user.UserDto;
import com.example.bulletinboard.entity.User;
import com.example.bulletinboard.repository.UserRepo;
import com.example.bulletinboard.service.UserMapper;
import com.example.bulletinboard.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    public UserServiceImpl(UserRepo userRepo, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto create(Register register) {
        return userMapper.toUserDto(userRepo.save(userMapper.toUser(register)));
    }

    @Override
    public UserDto getById(Integer id) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            throw new NoSuchElementException("Нет такого пользователя");
        }
        return userMapper.toUserDto(optionalUser.get());
    }

    @Override
    public List<UserDto> getAll() {
        return userRepo.findAll().stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(Integer id, UpdateUser updateUser) {
        User user = userRepo.findById(id).orElseThrow(()-> new NoSuchElementException("Пользователь не найден"));
        return userMapper.toUserDto(userRepo.save(userMapper.updateUser(user, updateUser)));
    }
}
