package ru.otus.service;

import ru.otus.domain.Genre;
import ru.otus.dto.GenreCreateDto;
import ru.otus.dto.GenreUpdateDto;

import java.util.List;


public interface GenreService {
    Genre create(Genre genre);

    Genre create(GenreCreateDto genreCreateDto);

    Genre update(Genre genre);

    Genre update(GenreUpdateDto genreUpdateDto);

    Genre read(String name);

    Genre read(Long id);

    List<Genre> getAll();
}
