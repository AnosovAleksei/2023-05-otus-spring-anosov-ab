package ru.otus.service;

import ru.otus.domain.Book;


import java.util.List;

public interface BookService {

    Book create(String name, String authorName, String genreName);

    Book update(String name, String authorName, String genreName);

    Book update(Book book);

    long count();

    List<Book> getAll();

    Book getByName(String name);

    Book getByID(Long bookId);

    void delete(String name);

}
