package com.example.ReactiveElectronicLibrary.mapper;

import com.example.ReactiveElectronicLibrary.entity.BookEntity;
import com.example.ReactiveElectronicLibrary.form.BookForm;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class BookMapper {

    public Mono<BookEntity> toEntity(BookForm bookForm, Long authorId) {
        return Mono.just(BookEntity.builder()
                .title(bookForm.getTitle())
                .description(bookForm.getDescription())
                .authorId(authorId)
                .build());
    }

//    public static Mono<BookView> toView(BookEntity bookEntity) {
//        return Mono.just(BookView.builder()
//                .id(bookEntity.getId())
//                .title(bookEntity.getTitle())
//                .description(bookEntity.getDescription())
//                .authorName(bookEntity.getAuthor().getName())
//                .authorSurname(bookEntity.getAuthor().getSurname())
//                .build());
//    }
}
