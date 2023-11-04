package com.example.bulletinboard.dto.ad;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class Ads {
    private Integer count;
    private List<AdDto> results;
}