package ru.otus.dao;

import ru.otus.domain.Genre;

import java.util.List;

public interface GenreDao {

    List<Genre> getAll();

    Genre getByName(String name);

    Genre create(Genre genre);
}
