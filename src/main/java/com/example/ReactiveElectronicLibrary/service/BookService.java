package com.example.ReactiveElectronicLibrary.service;

import com.example.ReactiveElectronicLibrary.form.BookForm;
import com.example.ReactiveElectronicLibrary.view.BookView;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {
    Mono<Long> save(BookForm bookForm, MultipartFile file);

    Mono<BookView> findBook(Long id);

    Flux<BookView> findAllBooks();

    Mono<Void> delete(Long id);
}
