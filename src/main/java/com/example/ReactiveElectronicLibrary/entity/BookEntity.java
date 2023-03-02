package com.example.ReactiveElectronicLibrary.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "books")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BookEntity {

    @Id
    private Long id;

    @NonNull
    private String title;

    @NonNull
    private String description;

    @Column("author_id")
    private Long authorId;
}
