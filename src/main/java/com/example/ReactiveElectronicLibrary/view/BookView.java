package com.example.ReactiveElectronicLibrary.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class BookView {

    private Long id;

    private String title;

    private String authorName;

    private String authorSurname;

    private String description;
}
