package com.example.bulletinboard.repository;

import com.example.bulletinboard.entity.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdRepo extends JpaRepository<Ad, Integer> {
    List<Ad> findAllByUserEmail(String email);
    List<Ad> findAllByTitle(String title);
}
