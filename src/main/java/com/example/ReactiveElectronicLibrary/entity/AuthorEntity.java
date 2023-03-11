package com.example.ReactiveElectronicLibrary.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "authors")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AuthorEntity {

    @Id
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String surname;
}
