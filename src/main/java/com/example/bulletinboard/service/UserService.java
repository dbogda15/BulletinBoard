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
    /**
     * Обновление информации о пользователе
     * @param updateUser
     * @return UserDto
     */
    UserDto updateUser(UpdateUser updateUser);

    /**
     * Обновление пароля
     * @param newPassword
     * @return {@code true} - если пароль обновлен успешно, {@code false} - если пароль не обновлен
     */
    boolean updatePassword(NewPassword newPassword);

    /**
     * Получить информацию о пользователе
     * @return UserDto
     */
    UserDto getInfoAboutUser();

    /**
     * Обновить фотографию профиля
     * @param avatar
     * @throws IOException
     */
    byte [] updateAvatar(MultipartFile avatar) throws IOException;

    /**
     * Загрузить (скачать) фото профиля
     * @param id
     * @param response
     * @return {@code true} - если фото загружено успешно
     * @throws IOException
     */
    boolean downloadAvatar(int id, HttpServletResponse response) throws IOException;
}
