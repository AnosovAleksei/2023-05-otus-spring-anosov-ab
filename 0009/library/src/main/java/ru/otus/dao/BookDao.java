package ru.otus.dao;


import ru.otus.domain.Book;

import java.util.List;


public interface BookDao {
    int count();

    String delateBook(String name);

    Book upgradeBook(String name, Long author_id, Long genre_id);

    Book saveBook(String name, Long author_id, Long genre_id);

    Book getBookByName(String name);

    List<Book> getAllBook();

}
