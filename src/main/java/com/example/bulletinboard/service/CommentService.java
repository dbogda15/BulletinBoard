package com.example.bulletinboard.service;

import com.example.bulletinboard.dto.comment.CommentDto;
import com.example.bulletinboard.dto.comment.Comments;
import com.example.bulletinboard.dto.comment.CreateOrUpdateComment;

public interface CommentService {
    /**
     * Создание комментария
     * @param adId
     * @param comment
     * @return CommentDto
     */
    CommentDto create(Integer adId, CreateOrUpdateComment comment);

    /**
     * Получение всех комментариев объявления
     * @param adId
     * @return Comments
     */
    Comments getComments(Integer adId);

    /**
     * Удаление комментария
     * @param adId
     * @param commentId
     */
    void delete(Integer adId, Integer commentId);

    /**
     * Обновление комменатрия
     * @param adId
     * @param commentId
     * @param comment
     * @return CommentDto
     */
    CommentDto update(Integer adId, Integer commentId, CreateOrUpdateComment comment);
}
