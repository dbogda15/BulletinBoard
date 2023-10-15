package com.example.bulletinboard.controller;

import com.example.bulletinboard.dto.user.NewPassword;
import com.example.bulletinboard.dto.user.UpdateUser;
import com.example.bulletinboard.dto.user.UserDto;
import com.example.bulletinboard.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = "Пользователи")
@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/set_password")
    public ResponseEntity<Boolean> setPassword (@RequestBody NewPassword newPassword){
        return ResponseEntity.ok(userService.updatePassword(newPassword));
    }

    @GetMapping("/me")
    public UserDto getUser(){
        return userService.getInfoAboutUser();
    }

    @PatchMapping("/me")
    public UserDto updateUser(@RequestBody UpdateUser updateUser){
        return userService.updateUser(updateUser);
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public byte[] updateImage(@RequestParam("image") MultipartFile image) throws IOException {
        return userService.updateAvatar(image);
    }
}
