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
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
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
    public Mono<Long> save(BookForm bookForm, FilePart file) {
        minioService.uploadFile(file);

        return authorRepository.findByNameAndSurname(bookForm.getAuthorName(), bookForm.getAuthorSurname())
                .switchIfEmpty(authorMapper.toEntity(bookForm)
                        .flatMap(authorRepository::save))
                .flatMap(authorMono -> bookMapper.toEntity(bookForm, authorMono.getId(), file.filename())
                        .flatMap(bookRepository::save)
                        .map(BookEntity::getId));
    }

    @Override
    public Mono<BookView> findBook(Long id) {
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
    public Mono<Void> edit(Long id, BookForm bookForm) {
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.error(new BookServiceException(HttpStatus.NOT_FOUND, "Book not found!!!")))
                .flatMap(bookEntity -> authorRepository.findById(bookEntity.getAuthorId())
                        .flatMap(authorEntity -> {
                            authorEntity.setName(bookForm.getAuthorName());
                            authorEntity.setSurname(bookForm.getAuthorSurname());
                            bookEntity.setTitle(bookForm.getTitle());
                            bookEntity.setDescription(bookForm.getDescription());
                            return authorRepository.save(authorEntity);
                        }).flatMap(updatedAuthor -> bookRepository.save(bookEntity))).then();

    }

    @Override
    public Mono<Void> download(String filename) {

        minioService.getFile(filename);

        return Mono.empty();

//        return bookRepository.findById(id)
//                .switchIfEmpty(Mono.error(new BookServiceException(HttpStatus.NOT_FOUND, "Book not found!!!")))
//                .flatMap(bookEntity -> minioService.getFile(bookEntity.getFilename()))
//                .then();
    }

    @Override
    public Mono<Void> delete(Long id) {
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.error(new BookServiceException(HttpStatus.NOT_FOUND, "Book or File not found!!!")))
                .flatMap(bookEntity -> {
                    minioService.deleteFile(bookEntity.getFilename());
                    return bookRepository.delete(bookEntity);
                });
    }


}
