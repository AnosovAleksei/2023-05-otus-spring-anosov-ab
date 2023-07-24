package ru.otus.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.BookDao;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BookService {

    private final AuthorService authorService;

    private final GenreService genreService;

    private final BookDao bookDao;

    @Transactional
    public Book create(String name, String authorName, String genreName) {
        Author author = authorService.create(authorName);
        Genre genre = genreService.create(genreName);

        return bookDao.create(name, author, genre);
    }

    @Transactional
    public Book update(String name, String authorName, String genreName) {
        Author author = authorService.create(authorName);
        Genre genre = genreService.create(genreName);

        return bookDao.upgrade(name, author, genre);
    }

    public int count() {
        return bookDao.count();
    }

    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Transactional
    public Book getByName(String name) {
        return bookDao.getByName(name);
    }

    @Transactional
    public String delate(String name) {
        return bookDao.delate(name);
    }
}
