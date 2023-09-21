package ru.otus.megration.service;

import org.springframework.stereotype.Service;
import ru.otus.domain.Commentary;
import ru.otus.megration.entity.Author;
import ru.otus.megration.entity.Book;
import ru.otus.megration.entity.Genre;

@Service
public class CommentaryService {
    public ru.otus.megration.entity.Commentary convert(Commentary commentary){

        ru.otus.megration.entity.Commentary comment = new ru.otus.megration.entity.Commentary();

        comment.setMessage(commentary.getMessage());

        Book book = new Book();
        book.setName(commentary.getBook().getName());
        book.setGenre(new Genre(commentary.getBook().getGenre().getName()));
        book.setAuthor(new Author(commentary.getBook().getAuthor().getName()));

        comment.setBook(book);

        return comment;
    }

}
