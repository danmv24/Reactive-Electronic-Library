package com.example.ReactiveElectronicLibrary.controller;

import com.example.ReactiveElectronicLibrary.form.BookForm;
import com.example.ReactiveElectronicLibrary.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/api/book")
public class BookController {

    private BookService bookService;


    @PostMapping("/add")
    public Mono<Long> addBook(@RequestPart("book") BookForm bookForm, @RequestPart("file") MultipartFile file) {
        return bookService.save(bookForm, file);
    }
}
