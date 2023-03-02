package com.example.ReactiveElectronicLibrary.repository;

import com.example.ReactiveElectronicLibrary.entity.AuthorEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AuthorRepository extends ReactiveCrudRepository<AuthorEntity, Long> {
    Mono<AuthorEntity> findByNameAndSurname(String name, String surname);
}
