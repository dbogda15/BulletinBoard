package com.example.bulletinboard.service.impl;

import com.example.bulletinboard.dto.comment.CommentDto;
import com.example.bulletinboard.dto.comment.Comments;
import com.example.bulletinboard.dto.comment.CreateOrUpdateComment;
import com.example.bulletinboard.entity.Comment;
import com.example.bulletinboard.service.CommentMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapperImpl implements CommentMapper {
    @Override
    public CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .pk(comment.getId())
                .authorFirstName(comment.getUser().getFirstName())
                .author(comment.getUser().getId())
                .authorImage(comment.getUser().getImage())
                .text(comment.getText())
                .createdAt(comment.getCreatedAt().toInstant(ZoneOffset.UTC))
                .build();
    }

    @Override
    public Comments toComments(List<Comment> commentsList) {
        Comments comments = new Comments();
        comments.setCount(commentsList.size());
        comments.setResults(commentsList.stream()
                .map(this::toCommentDto)
                .collect(Collectors.toList()));
        return comments;
    }

    @Override
    public Comment toComment(CreateOrUpdateComment updateComment, Comment comment) {
        comment.setText(updateComment.getText());
        comment.setCreatedAt(LocalDateTime.now());
        return comment;
    }
}
