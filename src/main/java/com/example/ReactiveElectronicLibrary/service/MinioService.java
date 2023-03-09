package com.example.ReactiveElectronicLibrary.service;

import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

public interface MinioService {
    Mono<Void> uploadFile(MultipartFile file);

    Mono<Void> getFile(String filename);

    Mono<Void> deleteFile(String filename);
}
