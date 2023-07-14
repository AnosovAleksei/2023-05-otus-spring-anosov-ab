package ru.otus.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Author;
import ru.otus.domain.Genre;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookService {
    private final AuthorDao authorDao;

    private final GenreDao genreDao;

    public Author createAuthor(String name) {
        return authorDao.createAuthor(name);
    }

    public Genre createGenre(String name) {
        return genreDao.createGenre(name);
    }

    public List<Author> getAllAuthor() {
        return authorDao.getAllAuthor();
    }

    public List<Genre> getAllGenre() {
        return genreDao.getAllGenre();
    }
}
