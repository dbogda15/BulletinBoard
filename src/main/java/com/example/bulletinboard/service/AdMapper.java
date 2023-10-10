package com.example.bulletinboard.service;

import com.example.bulletinboard.dto.ad.AdDto;
import com.example.bulletinboard.dto.ad.Ads;
import com.example.bulletinboard.dto.ad.CreateOrUpdateAd;
import com.example.bulletinboard.dto.ad.ExtendedAd;
import com.example.bulletinboard.entity.Ad;

import java.util.List;

public interface AdMapper {
    Ad fromAdUpdate(CreateOrUpdateAd createOrUpdateAd, Ad ad);
    Ad fromAdCreate(CreateOrUpdateAd createOrUpdateAd);
    Ads toAds(List<Ad> ads);
    AdDto toAdDto(Ad ad);
    ExtendedAd toExtendedAd(Ad ad);
}
