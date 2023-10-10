package com.example.bulletinboard.service;

import com.example.bulletinboard.dto.comment.CommentDto;
import com.example.bulletinboard.dto.comment.Comments;
import com.example.bulletinboard.dto.comment.CreateOrUpdateComment;
import com.example.bulletinboard.entity.Comment;

import java.util.List;

public interface CommentMapper {
    CommentDto toCommentDto(Comment comment);
    Comments toComments(List<Comment> comments);
    Comment toComment(CreateOrUpdateComment updateComment, Comment comment);
}
