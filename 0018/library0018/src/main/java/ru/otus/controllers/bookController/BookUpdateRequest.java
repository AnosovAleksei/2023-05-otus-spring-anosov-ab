package ru.otus.controllers.bookController;

import lombok.Data;

@Data
public class BookUpdateRequest {

    private Long bookId;

    private String name;

    private Long authorId;

    private Long genreId;
}
