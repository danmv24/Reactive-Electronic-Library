package com.example.ReactiveElectronicLibrary.controller;

import com.example.ReactiveElectronicLibrary.service.AuthorService;
import com.example.ReactiveElectronicLibrary.view.BookView;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@AllArgsConstructor
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/{id}")
    public Flux<BookView> getBooksByAuthor(@PathVariable(value = "id") Long id) {
        return authorService.findAllBooksByAuthor(id);
    }
}
