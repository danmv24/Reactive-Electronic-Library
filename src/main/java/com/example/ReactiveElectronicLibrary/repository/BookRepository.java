package com.example.ReactiveElectronicLibrary.repository;

import com.example.ReactiveElectronicLibrary.entity.BookEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends ReactiveCrudRepository<BookEntity, Long> {
}
