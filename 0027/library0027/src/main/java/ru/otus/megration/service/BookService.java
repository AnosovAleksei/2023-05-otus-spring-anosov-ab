package ru.otus.megration.service;

import org.springframework.stereotype.Service;
import ru.otus.domain.Book;
import ru.otus.megration.entity.Author;
import ru.otus.megration.entity.Genre;

@Service
public class BookService {
    public ru.otus.megration.entity.Book convert(Book book){
        System.out.println(book.getName());

        ru.otus.megration.entity.Book b = new ru.otus.megration.entity.Book();

        b.setName(book.getName());
        b.setGenre(new Genre(book.getGenre().getName()));
        b.setAuthor(new Author(book.getAuthor().getName()));

        System.out.println(b.getAuthor().getName());

        return b;
    }

}
