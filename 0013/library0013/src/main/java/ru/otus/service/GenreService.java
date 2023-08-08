package ru.otus.service;

import ru.otus.domain.Genre;


public interface GenreService {
    Genre create(String name);

    Iterable<Genre> getAll();
}
