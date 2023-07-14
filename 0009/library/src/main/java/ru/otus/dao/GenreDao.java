package ru.otus.dao;

import ru.otus.domain.Genre;

import java.util.List;

public interface GenreDao {

    List<Genre> getAllGenre();

    Genre getGenreByName(String name);

    Genre createGenre(String name);
}
