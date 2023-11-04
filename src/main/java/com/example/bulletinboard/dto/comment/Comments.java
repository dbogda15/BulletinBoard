package com.example.bulletinboard.dto.comment;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class Comments {
    private Integer count;
    private List<CommentDto> results;
}