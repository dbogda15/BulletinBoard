package com.example.bulletinboard.service.impl;

import com.example.bulletinboard.dto.ad.AdDto;
import com.example.bulletinboard.dto.ad.Ads;
import com.example.bulletinboard.dto.ad.CreateOrUpdateAd;
import com.example.bulletinboard.dto.user.Role;
import com.example.bulletinboard.entity.Ad;
import com.example.bulletinboard.entity.User;
import com.example.bulletinboard.repository.AdRepo;
import com.example.bulletinboard.repository.UserRepo;
import com.example.bulletinboard.service.AdMapper;
import com.example.bulletinboard.service.AdService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;

@Service
public class AdServiceImpl implements AdService {
    @Value("${path.to.ads.images}")
    private String pathToFolder;
    private final AdRepo adsRepo;
    private final AdMapper adMapper;
    private final UserRepo usersRepo;
    private final UserDetails userDetails;


    public AdServiceImpl(AdRepo adsRepo, AdMapper adMapper, UserRepo usersRepo, UserDetails userDetails) {
        this.adsRepo = adsRepo;
        this.adMapper = adMapper;
        this.usersRepo = usersRepo;
        this.userDetails = userDetails;
    }

    @Override
    public AdDto create(CreateOrUpdateAd createdAd, MultipartFile file) {
        Ad ad = adMapper.fromAdCreate(createdAd);
        User user = getUser();
        Path path = getPath(file, ad);
        ad.setUser(user);
        ad.setImage(path.toAbsolutePath().toString());
        adsRepo.save(adMapper.fromAdCreate(createdAd));
        return adMapper.toAdDto(adMapper.fromAdCreate(createdAd));
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
    public void deleteById(Integer id) {
        User user = getUser();
        Ad ad = getAdById(id);
        if (rightsVerification(user, ad)) {
            adsRepo.deleteById(id);
        }
    }

    @Override
    public byte[] updateAdImage(Integer id, MultipartFile image) throws IOException {
        Ad ad = adsRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Объявление не найдено"));
        if (ad.getImage()!=null){
            Files.deleteIfExists(Path.of(ad.getImage()));
        }
            ad.setImage(getPath(image, ad).toAbsolutePath().toString());
            adsRepo.save(ad);
            return image.getBytes();
    }

    private boolean rightsVerification(User user, Ad ad) {
        return (user.getRole().equals(Role.ADMIN) || ad.getUser().equals(user));
    }

    private User getUser() {
        return usersRepo.findByEmail(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    private Ad getAdById(Integer id) {
        return adsRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Объявление не найдено"));
    }

    private Path getPath(MultipartFile image, Ad ad) {
        return Path.of(pathToFolder, image.getName() + "Ad-" + ad.getId());
    }
}
