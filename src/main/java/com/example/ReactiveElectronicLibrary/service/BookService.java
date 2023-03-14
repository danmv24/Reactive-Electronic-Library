package com.example.ReactiveElectronicLibrary.service;

import com.example.ReactiveElectronicLibrary.form.BookForm;
import com.example.ReactiveElectronicLibrary.view.BookView;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {
    Mono<Long> save(BookForm bookForm, FilePart file);

    Mono<BookView> findBook(Long id);

    Flux<BookView> findAllBooks();

    Mono<Void> edit(Long id, BookForm bookForm);

    Mono<Void> download(String filename);

    Mono<Void> delete(Long id);
}
