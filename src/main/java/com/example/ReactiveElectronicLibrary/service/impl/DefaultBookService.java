package com.example.ReactiveElectronicLibrary.service.impl;

import com.example.ReactiveElectronicLibrary.entity.BookEntity;
import com.example.ReactiveElectronicLibrary.form.BookForm;
import com.example.ReactiveElectronicLibrary.mapper.AuthorMapper;
import com.example.ReactiveElectronicLibrary.mapper.BookMapper;
import com.example.ReactiveElectronicLibrary.repository.AuthorRepository;
import com.example.ReactiveElectronicLibrary.repository.BookRepository;
import com.example.ReactiveElectronicLibrary.service.BookService;
import com.example.ReactiveElectronicLibrary.service.MinioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class DefaultBookService implements BookService {

    private final MinioService minioService;

    private final BookMapper bookMapper;

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    @Override
    public Mono<Long> save(BookForm bookForm, MultipartFile file) {
        return Mono.fromCallable(() -> {
            minioService.uploadFile(file);
            return null;
        }).then(authorRepository.findByNameAndSurname(bookForm.getAuthorName(), bookForm.getAuthorSurname()))
                .flatMap(authorMono -> {
                    if (authorMono != null)
                       return bookRepository.save(bookMapper.toEntity(bookForm, authorMono.getId())).map(BookEntity::getId);
                    else
                        return authorRepository.save(AuthorMapper.toEntity(bookForm))
                                .flatMap(newAuthor -> bookRepository.save(bookMapper.toEntity(bookForm, newAuthor.getId())))
                                .map(BookEntity::getId);
                });
    }
}
