package com.example.ReactiveElectronicLibrary.service;

import com.example.ReactiveElectronicLibrary.view.BookView;
import reactor.core.publisher.Flux;

public interface AuthorService {
    Flux<BookView> findAllBooksByAuthor(Long id);
}
