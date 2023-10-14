package com.example.bulletinboard.service.impl;

import com.example.bulletinboard.dto.comment.CommentDto;
import com.example.bulletinboard.dto.comment.Comments;
import com.example.bulletinboard.dto.comment.CreateOrUpdateComment;
import com.example.bulletinboard.dto.user.Role;
import com.example.bulletinboard.entity.Ad;
import com.example.bulletinboard.entity.Comment;
import com.example.bulletinboard.entity.User;
import com.example.bulletinboard.repository.AdRepo;
import com.example.bulletinboard.repository.CommentRepo;
import com.example.bulletinboard.repository.UserRepo;
import com.example.bulletinboard.service.CommentMapper;
import com.example.bulletinboard.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final AdRepo adsRepo;
    private final CommentRepo commentRepo;
    private final CommentMapper commentMapper;
    private final UserRepo userRepo;
    private final UserDetails userDetails;

    @Override
    public CommentDto create(Integer adId, CreateOrUpdateComment createOrUpdateComment) {
        Ad ad = getAdById(adId);
        User user = getUser();
        Comment comment = commentMapper.fromCommCreate(createOrUpdateComment);
        comment.setUser(user);
        comment.setAd(ad);
        commentRepo.save(comment);
        return commentMapper.toCommentDto(comment);
    }

    @Override
    public Comments getComments(Integer id) {
        return commentMapper.toComments(commentRepo.findAll());
    }

    @Override
    public void delete(Integer adId, Integer commentId) {
        User user = getUser();
        Ad ad = getAdById(adId);
        Comment comment = commentRepo.findByIdAndAd_Id(commentId, adId);
        if (rightsVerification(user, ad)){
            commentRepo.delete(comment);
        }
        else throw new UnsupportedOperationException("Нет прав на удаление комментария");
    }

    @Override
    public CommentDto update(Integer adId, Integer commentId, CreateOrUpdateComment createOrUpdateComment) {
        User user = getUser();
        Ad ad = getAdById(adId);
        Comment comment = commentRepo.findByIdAndAd_Id(commentId, adId);
        if (rightsVerification(user, ad)){
            return commentMapper.toCommentDto(commentRepo.save(commentMapper.fromCommUpdate(createOrUpdateComment, comment)));
        }
        else throw new UnsupportedOperationException("Нет прав на изменение комментария");
    }

    private boolean rightsVerification(User user, Ad ad) {
        return (user.getRole().equals(Role.ADMIN) || ad.getUser().equals(user));
    }

    private User getUser() {
        return userRepo.findByEmail(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    private Ad getAdById(Integer id) {
        return adsRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Объявление не найдено"));
    }
}
