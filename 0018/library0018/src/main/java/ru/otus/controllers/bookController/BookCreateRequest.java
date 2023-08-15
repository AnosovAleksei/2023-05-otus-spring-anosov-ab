package ru.otus.controllers.bookController;

import lombok.Data;

@Data
public class BookCreateRequest {
    private String name;

    private Long authorId;

    private Long genreId;
}
