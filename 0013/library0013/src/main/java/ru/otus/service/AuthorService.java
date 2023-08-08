package ru.otus.service;

import ru.otus.domain.Author;

public interface AuthorService {
    Author create(String name);

    Iterable<Author> getAll();

}
