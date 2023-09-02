package ru.otus.dao;

import ru.otus.domain.Author;

import java.util.List;

public interface AuthorDao {

    List<Author> getAll();

    Author getByName(String name);

    Author create(Author author);
}
