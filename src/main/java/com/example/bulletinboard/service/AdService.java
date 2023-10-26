package com.example.bulletinboard.service;

import com.example.bulletinboard.dto.ad.AdDto;
import com.example.bulletinboard.dto.ad.Ads;
import com.example.bulletinboard.dto.ad.CreateOrUpdateAd;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AdService {
    AdDto create(CreateOrUpdateAd ad, MultipartFile file) throws IOException;
    AdDto getById(Integer id);
    Ads getAll();
    Ads getAllByUserName();
    AdDto update(Integer id, CreateOrUpdateAd createOrUpdateAd);
    void deleteById(Integer id) throws IOException;
    byte[] updateAdImage(Integer id, MultipartFile image) throws IOException;
    void downloadImage (Integer id, HttpServletResponse response) throws IOException;
    Ads findByTitle(String title);
}
