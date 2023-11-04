package com.example.bulletinboard.service;

import com.example.bulletinboard.dto.ad.AdDto;
import com.example.bulletinboard.dto.ad.Ads;
import com.example.bulletinboard.dto.ad.CreateOrUpdateAd;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AdService {
    /**
     * Создание объявления
     * @param ad
     * @param file
     * @return AdDto
     * @throws IOException
     */
    AdDto create(CreateOrUpdateAd ad, MultipartFile file) throws IOException;

    /**
     * Поиск объявления по его id
     * @param id
     * @return AdDto
     */
    AdDto getById(Integer id);

    /**
     * Получить все объявления
     * @return
     */
    Ads getAll();

    /**
     * Получить все объявления конкретного пользователя
     * @return Ads
     */
    Ads getAllByUserName();

    /**
     * Обновить объявление
     * @param id
     * @param createOrUpdateAd
     * @return AdDto
     */
    AdDto update(Integer id, CreateOrUpdateAd createOrUpdateAd);

    /**
     * Удалить объявление по его id
     * @param id
     * @throws IOException
     */
    void deleteById(Integer id) throws IOException;

    /**
     * Обновить фото объявления
     * @param id
     * @param image
     * @throws IOException
     */
    byte[] updateAdImage(Integer id, MultipartFile image) throws IOException;

    /**
     * Загрузить фото объявления
     * @param id
     * @param response
     * @throws IOException
     */
    void downloadImage (Integer id, HttpServletResponse response) throws IOException;

    /**
     * Поиск объявления по названию
     * @param title
     * @return Ads
     */
    Ads findByTitle(String title);
}
