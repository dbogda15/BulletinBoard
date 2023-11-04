package com.example.bulletinboard.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public class CommentDto {
    private int author;
    private String authorImage;
    private String authorFirstName;
    private Instant createdAt;
    private int pk;
    private String text;
}
