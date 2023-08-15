package ru.otus.controllers.bookController;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDto {
    private Long id;

    private String name;

    private String authorName;

    private String genreName;


}
