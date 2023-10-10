package com.example.bulletinboard.controller;

import com.example.bulletinboard.dto.ad.AdDto;
import com.example.bulletinboard.dto.ad.Ads;
import com.example.bulletinboard.dto.ad.CreateOrUpdateAd;
import com.example.bulletinboard.service.AdService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = "Объявления")
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdController {
    private final AdService adService;

    @GetMapping

    public Ads getAllAds() {
        return adService.getAll();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AdDto create(@RequestPart("properties") CreateOrUpdateAd ad,
                        @RequestPart("image") MultipartFile image) throws IOException {
        return adService.create(ad, image);
    }

    @GetMapping("/{id}")
    public AdDto getAdById(@PathVariable Integer id) {
        return adService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        adService.deleteById(id);
    }

    @PatchMapping("/{id}")
    public AdDto updateAd(@RequestBody CreateOrUpdateAd createOrUpdateAd,
                          @PathVariable Integer id) {
        return adService.update(id, createOrUpdateAd);
    }

    @GetMapping("/me")
    public Ads getUsersAd() {
        return adService.getAllByUserName();
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public byte[] updateAdImage(@PathVariable Integer id,
                                @RequestParam("image") MultipartFile image) throws IOException {
        return adService.updateAdImage(id, image);
    }
}
