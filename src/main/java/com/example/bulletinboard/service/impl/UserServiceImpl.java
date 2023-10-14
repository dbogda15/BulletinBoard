package com.example.bulletinboard.service.impl;

import com.example.bulletinboard.dto.user.NewPassword;
import com.example.bulletinboard.dto.user.UpdateUser;
import com.example.bulletinboard.dto.user.UserDto;
import com.example.bulletinboard.entity.User;
import com.example.bulletinboard.repository.UserRepo;
import com.example.bulletinboard.service.UserMapper;
import com.example.bulletinboard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final UserDetails userDetails;
    private final UserDetailsManager userDetailsManager;

    @Value("${path.to.avatar.images}")
    private String pathToAvatarFolder;

    @Override
    public UserDto updateUser(UpdateUser updateUser) {
        User user = getUser();
        return userMapper.toUserDto(userRepo.save(userMapper.updateUser(user, updateUser)));
    }

    @Override
    public boolean updatePassword(NewPassword newPassword) {
        if (checkPassword(newPassword)){
            userDetailsManager.changePassword(newPassword.getCurrentPassword(), newPassword.getNewPassword());
            return true;
        }
        return false;
    }

    @Override
    public UserDto getInfoAboutUser() {
        User user = getUser();
        return userMapper.toUserDto(user);
    }

    @Override
    @Transactional
    public byte[] updateAvatar(MultipartFile avatar) throws IOException {
        User user = getUser();
        Path path = Path.of(pathToAvatarFolder,user.getId() + "." + avatar.getOriginalFilename());
        Files.createDirectories(path.getParent());
        Files.deleteIfExists(path);

        try (InputStream is = avatar.getInputStream();
             OutputStream os = Files.newOutputStream(path, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }

        user.setImage(path.toString());
        userRepo.save(user);
        return avatar.getBytes();
    }

    private boolean checkPassword(NewPassword password){
        return (password!= null && !password.getNewPassword().isEmpty() && !password.getNewPassword().isBlank()
                && !password.getCurrentPassword().isEmpty() && !password.getCurrentPassword().isBlank());
    }

    private User getUser(){
        return userRepo.findByEmail(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Пользователя не существует!"));
    }
}
