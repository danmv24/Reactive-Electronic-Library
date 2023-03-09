package com.example.ReactiveElectronicLibrary.mapper;

import com.example.ReactiveElectronicLibrary.entity.AuthorEntity;
import com.example.ReactiveElectronicLibrary.entity.BookEntity;
import com.example.ReactiveElectronicLibrary.form.BookForm;
import com.example.ReactiveElectronicLibrary.view.BookView;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class BookMapper {

    public Mono<BookEntity> toEntity(BookForm bookForm, Long authorId, String filename) {
        return Mono.just(BookEntity.builder()
                .title(bookForm.getTitle())
                .filename(filename)
                .description(bookForm.getDescription())
                .authorId(authorId)
                .build());
    }

    public Mono<BookView> toView(BookEntity bookEntity, AuthorEntity author) {
        return Mono.just(BookView.builder()
                .id(bookEntity.getId())
                .title(bookEntity.getTitle())
                .authorName(author.getName())
                .authorSurname(author.getSurname())
                .description(bookEntity.getDescription())
                .build());
    }
}
