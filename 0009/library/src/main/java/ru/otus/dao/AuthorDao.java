package ru.otus.dao;

import ru.otus.domain.Author;

import java.util.List;

public interface AuthorDao {

    List<Author> getAllAuthor();

    Author getAuthorByName(String name);

    void createAuthor(String name);
}
