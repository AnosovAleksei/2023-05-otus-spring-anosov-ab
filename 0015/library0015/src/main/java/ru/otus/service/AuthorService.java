package ru.otus.service;

import ru.otus.domain.Author;

import java.util.List;

public interface AuthorService {
    Author create(Author author);


    Author update(Author author);


    Author read(String name);


    List<Author> getAll();

}
