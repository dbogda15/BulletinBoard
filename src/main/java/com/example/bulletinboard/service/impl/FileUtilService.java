package com.example.bulletinboard.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class FileUtilService {
    public void uploadFile(MultipartFile file, Path path) throws IOException {
        Files.createDirectories(path.getParent());
        Files.deleteIfExists(path);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(path, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 2048);
             BufferedOutputStream bos = new BufferedOutputStream(os, 2048)
        ) {
            bis.transferTo(bos);
        }
    }

    public void downloadFile(HttpServletResponse response, String imagePath) throws IOException {
        Path path = Path.of(imagePath);
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()) {
            response.setStatus(200);
            is.transferTo(os);
        }
    }
}
