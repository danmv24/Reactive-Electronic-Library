package com.example.ReactiveElectronicLibrary.service;

import org.springframework.web.multipart.MultipartFile;

public interface MinioService {
    void uploadFile(MultipartFile file);

    void getFile(String filename);

    void deleteFile(String filename);
}
