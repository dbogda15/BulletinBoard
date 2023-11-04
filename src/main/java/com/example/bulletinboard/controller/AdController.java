package com.example.bulletinboard.controller;

import com.example.bulletinboard.dto.ad.AdDto;
import com.example.bulletinboard.dto.ad.Ads;
import com.example.bulletinboard.dto.ad.CreateOrUpdateAd;
import com.example.bulletinboard.dto.ad.ExtendedAd;
import com.example.bulletinboard.service.AdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api(tags = "Объявления")
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Запрос выполнен успешно"),
        @ApiResponse(code = 400, message = "Ошибка в параметрах запроса"),
        @ApiResponse(code = 404, message = "Несуществующий URL"),
        @ApiResponse(code = 500, message = "Ошибка со стороны сервера")
})
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
    public ExtendedAd getAdById(@PathVariable Integer id) {
        return adService.getById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            adService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (IOException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
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

    @GetMapping(value = "/image/{adId}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public void downloadAdImageFromDB(@PathVariable int adId, HttpServletResponse response) throws IOException {
        adService.downloadImage(adId, response);
    }

    @GetMapping("/find")
    public ResponseEntity<Ads> findAdsByTitle(@RequestParam String title) {
        return ResponseEntity.ok(adService.findByTitle(title));
    }
}
