package com.example.ReactiveElectronicLibrary.service.impl;

import com.example.ReactiveElectronicLibrary.entity.BookEntity;
import com.example.ReactiveElectronicLibrary.exception.BookServiceException;
import com.example.ReactiveElectronicLibrary.form.BookForm;
import com.example.ReactiveElectronicLibrary.mapper.AuthorMapper;
import com.example.ReactiveElectronicLibrary.mapper.BookMapper;
import com.example.ReactiveElectronicLibrary.repository.AuthorRepository;
import com.example.ReactiveElectronicLibrary.repository.BookRepository;
import com.example.ReactiveElectronicLibrary.service.BookService;
import com.example.ReactiveElectronicLibrary.service.MinioService;
import com.example.ReactiveElectronicLibrary.view.BookView;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class DefaultBookService implements BookService {

    private final MinioService minioService;

    private final AuthorMapper authorMapper;

    private final BookMapper bookMapper;

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    @Override
    public Mono<Long> save(BookForm bookForm, MultipartFile file) {
//        return Mono.fromCallable(() -> {
//            minioService.uploadFile(file);
//            return null;
//        }).then(authorRepository.findByNameAndSurname(bookForm.getAuthorName(), bookForm.getAuthorSurname()))
//                .flatMap(authorMono -> {
//                    if (authorMono != null) {
//                        return bookRepository.save(bookMapper.toEntity(bookForm, authorMono.getId())).map(BookEntity::getId);
//                    } else {
//                        return authorRepository.save(AuthorMapper.toEntity(bookForm))
//                                .flatMap(newAuthor -> bookRepository.save(bookMapper.toEntity(bookForm, newAuthor.getId())))
//                                .map(BookEntity::getId);
//                    }
//                });


        minioService.uploadFile(file);

        return  authorRepository.findByNameAndSurname(bookForm.getAuthorName(), bookForm.getAuthorSurname())
                .flatMap(authorMono -> {
                    if (authorMono != null) {
                        return bookMapper.toEntity(bookForm, authorMono.getId(), file.getOriginalFilename())
                                .flatMap(bookRepository::save)
                                .map(BookEntity::getId);
                    } else {
                        return authorMapper.toEntity(bookForm)
                                .flatMap(authorRepository::save)
                                .flatMap(newAuthor ->bookMapper.toEntity(bookForm, newAuthor.getId(), file.getOriginalFilename()))
                                .flatMap(bookRepository::save)
                                .map(BookEntity::getId);
                    }
                });
    }

    @Override
    public Mono<BookView> findBook(Long id) {
//        return bookRepository.findById(id)
//                .flatMap(bookEntity -> {
//                    if (bookEntity != null) {
////                        Mono<AuthorEntity> author = authorRepository.findById(bookEntity.getAuthorId());
//                        return bookMapper.toView(bookEntity);
//                    } else {
//                        return Mono.error(new BookServiceException(HttpStatus.NOT_FOUND, "Book not found!"));
//                    }
//                });

//        return bookRepository.findById(id)
//                .flatMap(bookEntity -> {
//                    Mono<AuthorEntity> author = authorRepository.findById(bookEntity.getAuthorId());
//                    return bookMapper.toView(bookEntity, author);
//                })
//                .switchIfEmpty(Mono.error(new BookServiceException(HttpStatus.NOT_FOUND, "Book not found!")));

        return bookRepository.findById(id)
                .flatMap(bookEntity -> authorRepository.findById(bookEntity.getAuthorId())
                        .flatMap(author -> bookMapper.toView(bookEntity, author)))
                .switchIfEmpty(Mono.error(new BookServiceException(HttpStatus.NOT_FOUND, "Book not found!")));

    }

    @Override
    public Flux<BookView> findAllBooks() {
        return bookRepository.findAll()
                .flatMap(bookEntity -> authorRepository.findById(bookEntity.getAuthorId())
                        .flatMap(author -> bookMapper.toView(bookEntity, author)));
    }

    @Override
    public Mono<Void> delete(Long id) {
        return bookRepository.findById(id)
                .flatMap(bookEntity -> {
                    bookRepository.deleteById(id);
                    return minioService.deleteFile(bookEntity.getFilename());
                })
                .switchIfEmpty(Mono.error(new BookServiceException(HttpStatus.NOT_FOUND, "Book or File not found!!!")));
    }


}
