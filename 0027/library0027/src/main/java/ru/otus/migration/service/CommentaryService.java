package ru.otus.migration.service;

import org.springframework.stereotype.Service;
import ru.otus.domain.Commentary;
import ru.otus.migration.entity.Author;
import ru.otus.migration.entity.Book;
import ru.otus.migration.entity.Genre;

@Service
public class CommentaryService {
    public ru.otus.migration.entity.Commentary convert(Commentary commentary) {

        ru.otus.migration.entity.Commentary comment = new ru.otus.migration.entity.Commentary();

        comment.setMessage(commentary.getMessage());

        Book book = new Book();
        book.setName(commentary.getBook().getName());
        book.setGenre(new Genre(commentary.getBook().getGenre().getName()));
        book.setAuthor(new Author(commentary.getBook().getAuthor().getName()));

        comment.setBook(book);

        return comment;
    }

}
