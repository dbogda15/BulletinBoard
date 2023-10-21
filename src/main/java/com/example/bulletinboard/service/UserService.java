package com.example.bulletinboard.service;

import com.example.bulletinboard.dto.user.NewPassword;
import com.example.bulletinboard.dto.user.Register;
import com.example.bulletinboard.dto.user.UpdateUser;
import com.example.bulletinboard.dto.user.UserDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface UserService {
    UserDto updateUser(UpdateUser updateUser);
    boolean updatePassword(NewPassword newPassword);
    UserDto getInfoAboutUser();
    byte [] updateAvatar(MultipartFile avatar) throws IOException;
    boolean downloadAvatar(int id, HttpServletResponse response) throws IOException;
}
