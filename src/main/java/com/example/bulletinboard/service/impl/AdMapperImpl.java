package com.example.bulletinboard.service.impl;

import com.example.bulletinboard.dto.ad.AdDto;
import com.example.bulletinboard.dto.ad.Ads;
import com.example.bulletinboard.dto.ad.CreateOrUpdateAd;
import com.example.bulletinboard.dto.ad.ExtendedAd;
import com.example.bulletinboard.entity.Ad;
import com.example.bulletinboard.service.AdMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AdMapperImpl implements AdMapper {

    @Override
    public Ad fromAdUpdate(CreateOrUpdateAd createOrUpdateAd, Ad ad) {
        ad.setTitle(createOrUpdateAd.getTitle());
        ad.setPrice(createOrUpdateAd.getPrice());
        ad.setDescription(createOrUpdateAd.getDescription());
        return ad;
    }

    @Override
    public Ad fromAdCreate(CreateOrUpdateAd createOrUpdateAd) {
        return Ad.builder()
                .title(createOrUpdateAd.getTitle())
                .price(createOrUpdateAd.getPrice())
                .description(createOrUpdateAd.getDescription())
                .build();
    }

    @Override
    public Ads toAds(List<Ad> list) {
        Ads ads = new Ads();
        ads.setCount(list.size());
        ads.setResults(list.stream()
                .map(this::toAdDto)
                .collect(Collectors.toList()));
        return ads;
    }

    @Override
    public AdDto toAdDto(Ad ad) {
        return AdDto.builder()
                .pk(ad.getId())
                .price(ad.getPrice())
                .title(ad.getTitle())
                .image(ad.getImage())
                .author(ad.getUser().getId())
                .build();
    }

    @Override
    public ExtendedAd toExtendedAd(Ad ad) {
        return ExtendedAd.builder()
                .pk(ad.getId())
                .price(ad.getPrice())
                .title(ad.getTitle())
                .description(ad.getDescription())
                .image(ad.getImage())
                .email(ad.getUser().getEmail())
                .phone(ad.getUser().getPhone())
                .authorFirstName(ad.getUser().getFirstName())
                .authorLastName(ad.getUser().getLastName())
                .build();
    }
}

