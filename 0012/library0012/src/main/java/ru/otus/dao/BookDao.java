package ru.otus.dao;


import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;


public interface BookDao {
    long count();

    void delate(String name);

    Book upgrade(String name, Author author, Genre genre);

    Book update(Book book);

    Book create(Book book);

    Book getByName(String name);

    Book getById(Long bookId);

    List<Book> getAll();

}
