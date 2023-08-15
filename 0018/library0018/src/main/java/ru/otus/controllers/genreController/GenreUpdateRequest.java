package ru.otus.controllers.genreController;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class GenreUpdateRequest {

    private Long id;

    private String name;
}
