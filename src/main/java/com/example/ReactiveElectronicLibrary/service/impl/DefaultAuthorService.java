package com.example.ReactiveElectronicLibrary.service.impl;

import com.example.ReactiveElectronicLibrary.exception.AuthorNotFoundException;
import com.example.ReactiveElectronicLibrary.mapper.BookMapper;
import com.example.ReactiveElectronicLibrary.repository.AuthorRepository;
import com.example.ReactiveElectronicLibrary.repository.BookRepository;
import com.example.ReactiveElectronicLibrary.service.AuthorService;
import com.example.ReactiveElectronicLibrary.view.BookView;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class DefaultAuthorService implements AuthorService {

    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    @Override
    public Flux<BookView> findAllBooksByAuthor(Long id) {
        return authorRepository.findById(id)
                .switchIfEmpty(Mono.error(new AuthorNotFoundException(HttpStatus.NOT_FOUND, "Author not found!!!")))
                .flatMapMany(author -> bookRepository.findBooksByAuthorId(author.getId())
                        .flatMap(bookEntity -> bookMapper.toView(bookEntity, author)));
    }
}
