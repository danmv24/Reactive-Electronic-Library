package com.example.ReactiveElectronicLibrary.mapper;

import com.example.ReactiveElectronicLibrary.entity.AuthorEntity;
import com.example.ReactiveElectronicLibrary.form.BookForm;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthorMapper {

    public Mono<AuthorEntity> toEntity(BookForm bookForm) {
        return Mono.just(AuthorEntity.builder()
                .name(bookForm.getAuthorName())
                .surname(bookForm.getAuthorSurname())
                .build());
    }
}
