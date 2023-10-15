package com.example.bulletinboard.repository;

import com.example.bulletinboard.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
    Comment findByIdAndAd_Id(Integer commentId, Integer adId);
    void deleteAllByAd_Id(Integer id);
    List<Comment> findAllByAd_Id(Integer adId);
}
