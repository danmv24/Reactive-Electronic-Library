package com.example.ReactiveElectronicLibrary.controller;

import com.example.ReactiveElectronicLibrary.form.BookForm;
import com.example.ReactiveElectronicLibrary.service.BookService;
import com.example.ReactiveElectronicLibrary.view.BookView;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private BookService bookService;


    @PostMapping("/add")
    public Mono<Long> addBook(@RequestPart("book") BookForm bookForm, @RequestPart("file") MultipartFile file) {
        return bookService.save(bookForm, file);
    }

    @GetMapping("/{id}")
    public Mono<BookView> getBookInformation(@PathVariable(value = "id") Long id) {
        return bookService.findBook(id);
    }

    @GetMapping
    public Flux<BookView> getAllBooks() {
        return bookService.findAllBooks();
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteBook(@PathVariable(value = "id") Long id) {
        return bookService.delete(id);
    }
}
