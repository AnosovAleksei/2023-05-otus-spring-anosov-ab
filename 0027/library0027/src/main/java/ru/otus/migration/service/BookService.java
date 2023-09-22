package ru.otus.migration.service;

import org.springframework.stereotype.Service;
import ru.otus.domain.Book;
import ru.otus.migration.entity.Author;
import ru.otus.migration.entity.Genre;

@Service
public class BookService {
    public ru.otus.migration.entity.Book convert(Book book) {
        ru.otus.migration.entity.Book b = new ru.otus.migration.entity.Book();

        b.setName(book.getName());
        b.setGenre(new Genre(book.getGenre().getName()));
        b.setAuthor(new Author(book.getAuthor().getName()));
        return b;
    }

}
