package com.example.ReactiveElectronicLibrary.mapper;

import com.example.ReactiveElectronicLibrary.entity.AuthorEntity;
import com.example.ReactiveElectronicLibrary.form.BookForm;

public class AuthorMapper {

    public static AuthorEntity toEntity(BookForm bookForm) {
        return AuthorEntity.builder()
                .name(bookForm.getAuthorName())
                .surname(bookForm.getAuthorSurname())
                .build();
    }
}
