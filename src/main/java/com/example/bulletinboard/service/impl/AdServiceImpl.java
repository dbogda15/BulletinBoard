package com.example.bulletinboard.service.impl;

import com.example.bulletinboard.dto.ad.AdDto;
import com.example.bulletinboard.dto.ad.Ads;
import com.example.bulletinboard.dto.ad.CreateOrUpdateAd;
import com.example.bulletinboard.dto.user.Role;
import com.example.bulletinboard.entity.Ad;
import com.example.bulletinboard.entity.User;
import com.example.bulletinboard.repository.AdRepo;
import com.example.bulletinboard.repository.CommentRepo;
import com.example.bulletinboard.repository.UserRepo;
import com.example.bulletinboard.service.AdMapper;
import com.example.bulletinboard.service.AdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;

@Service
public class AdServiceImpl implements AdService {
    @Value("${path.to.ads.images}")
    private String pathToFolder;
    private final AdRepo adsRepo;
    private final AdMapper adMapper;
    private final UserRepo userRepo;
    private final CommentRepo commentRepo;
    private final UserDetails userDetails;
    private final FileUtilService fileUtilService;

    public AdServiceImpl(AdRepo adsRepo, AdMapper adMapper, UserRepo userRepo, CommentRepo commentRepo, UserDetails userDetails, FileUtilService fileUtilService) {
        this.adsRepo = adsRepo;
        this.adMapper = adMapper;
        this.userRepo = userRepo;
        this.commentRepo = commentRepo;
        this.userDetails = userDetails;
        this.fileUtilService = fileUtilService;
    }

    @Override
    public AdDto create(CreateOrUpdateAd createdAd, MultipartFile file) throws IOException {
        Ad ad = adMapper.fromAdCreate(createdAd);
        User user = getUser();
        ad.setUser(user);
        loadImage(ad, file);
        adsRepo.save(ad);
        return adMapper.toAdDto(ad);
    }

    @Override
    public AdDto getById(Integer id) {
        Ad ad = getAdById(id);
        return adMapper.toAdDto(ad);
    }

    @Override
    public Ads getAll() {
        return adMapper.toAds(adsRepo.findAll());
    }

    @Override
    public Ads getAllByUserName() {
        User user = getUser();
        return adMapper.toAds(adsRepo.findAllByUserEmail(user.getEmail()));
    }

    @Override
    public AdDto update(Integer id, CreateOrUpdateAd createOrUpdateAd) {
        User user = getUser();
        Ad ad = getAdById(id);
        if (rightsVerification(user, ad)) {
            return adMapper.toAdDto(adsRepo.save(adMapper.fromAdUpdate(createOrUpdateAd, ad)));
        }
        throw new IllegalArgumentException("Нет прав для этого действия!");
    }

    @Override
    public void deleteById(Integer id) throws IOException {
        User user = getUser();
        Ad ad = getAdById(id);
        if (rightsVerification(user, ad)) {
            commentRepo.deleteAllByAd_Id(id);
            adsRepo.deleteById(id);
            Files.deleteIfExists(Path.of(ad.getImage()));
        }
    }

    @Override
    public byte[] updateAdImage(Integer id, MultipartFile image) throws IOException {
        Ad ad = adsRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Объявление не найдено"));
        if (ad.getImage()!=null){
            Files.deleteIfExists(Path.of(ad.getImage()));
        }
        loadImage(ad, image);
        adsRepo.save(ad);
        return image.getBytes();
    }

    @Override
    public void downloadImage(Integer id, HttpServletResponse response) throws IOException {
        Ad ad  = getAdById(id);
        String imagePath = ad.getImage();
        fileUtilService.downloadFile(response, imagePath);
    }

    private boolean rightsVerification(User user, Ad ad) {
        return (user.getRole() == Role.ADMIN || ad.getUser().equals(user));
    }

    private User getUser() {
        return userRepo.findByEmail(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    private Ad getAdById(Integer id) {
        return adsRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Объявление не найдено"));
    }

    private Path getPath(MultipartFile image) {
        return Path.of(pathToFolder, image.getOriginalFilename());
    }

    private void loadImage(Ad ad, MultipartFile image) throws IOException {
        Path path = getPath(image);
        fileUtilService.uploadFile(image, path);
        ad.setImage(path.toAbsolutePath().toString());
    }
}
