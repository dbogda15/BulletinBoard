package com.example.bulletinboard.service;

import com.example.bulletinboard.dto.comment.CommentDto;
import com.example.bulletinboard.dto.comment.Comments;
import com.example.bulletinboard.dto.comment.CreateOrUpdateComment;

public interface CommentService {
    CommentDto create(Integer adId, CreateOrUpdateComment comment);
    Comments getComments(Integer adId);
    void delete(Integer adId, Integer commentId);
    CommentDto update(Integer adId, Integer commentId, CreateOrUpdateComment comment);
}
