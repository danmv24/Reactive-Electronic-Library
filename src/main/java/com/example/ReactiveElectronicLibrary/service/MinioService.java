package com.example.ReactiveElectronicLibrary.service;


import org.springframework.http.codec.multipart.FilePart;

public interface MinioService {
    void uploadFile(FilePart file);

    void getFile(String filename);

    void deleteFile(String filename);
}
