package ru.otus.service;

import ru.otus.domain.Genre;

import java.util.List;


public interface GenreService {
    Genre create(Genre genre);

    Genre update(Genre genre);

    Genre read(String name);

    List<Genre> getAll();
}
