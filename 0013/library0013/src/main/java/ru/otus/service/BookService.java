package ru.otus.service;

import ru.otus.domain.Book;


public interface BookService {

    Book create(Book book);

    Book update(Book book);

    long count();

    Iterable<Book> getAll();

    Book getByName(String name);

    Book getByID(Long bookId);

    void delete(String name);

}
