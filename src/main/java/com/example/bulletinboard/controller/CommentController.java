package com.example.bulletinboard.controller;

import com.example.bulletinboard.dto.comment.CommentDto;
import com.example.bulletinboard.dto.comment.Comments;
import com.example.bulletinboard.dto.comment.CreateOrUpdateComment;
import com.example.bulletinboard.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Комментарии")
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
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{id}/comments")
    public CommentDto create(@PathVariable Integer id, @RequestBody CreateOrUpdateComment comment){
        return commentService.create(id, comment);
    }

    @GetMapping("/{id}/comments")
    public Comments getCommentsByAdId(@PathVariable Integer id){
        return commentService.getComments(id);
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    public void deleteComment(@PathVariable Integer adId, @PathVariable Integer commentId){
        commentService.delete(adId, commentId);
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    public CommentDto update(@PathVariable Integer adId, @PathVariable Integer commentId, @RequestBody CreateOrUpdateComment comment){
        return commentService.update(adId, commentId, comment);
    }
}
