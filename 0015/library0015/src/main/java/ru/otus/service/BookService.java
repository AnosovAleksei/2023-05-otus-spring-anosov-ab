package ru.otus.service;

import ru.otus.domain.Book;

import java.util.List;


public interface BookService {

    Book create(Book book);

    Book update(Book book);

    long count();

    List<Book> getAll();

    Book getByName(String name);

    Book getByID(String bookId);

    void delete(String name);

}
