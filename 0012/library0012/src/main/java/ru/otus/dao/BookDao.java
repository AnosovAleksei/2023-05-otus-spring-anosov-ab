package ru.otus.dao;


import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;


public interface BookDao {
    int count();

    String delateBook(String name);

    Book upgradeBook(String name, Author author, Genre genre);

    Book saveBook(String name, Author author, Genre genre);

    Book getBookByName(String name);

    Book getBookById(Long bookId);

    List<Book> getAllBook();

}
