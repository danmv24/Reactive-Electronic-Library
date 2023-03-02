package com.example.ReactiveElectronicLibrary.service;

import com.example.ReactiveElectronicLibrary.form.BookForm;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

public interface BookService {
    Mono<Long> save(BookForm bookForm, MultipartFile file);
}
