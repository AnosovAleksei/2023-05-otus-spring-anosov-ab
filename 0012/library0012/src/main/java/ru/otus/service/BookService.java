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
    public Book createNewBook(String name, String authorName, String genreName) {
        Author author = authorService.createAuthor(authorName);
        Genre genre = genreService.createGenre(genreName);

        return bookDao.createBook(name, author, genre);
    }

    @Transactional
    public Book updateBook(String name, String authorName, String genreName) {
        Author author = authorService.createAuthor(authorName);
        Genre genre = genreService.createGenre(genreName);

        return bookDao.upgradeBook(name, author, genre);
    }

    public int count() {
        return bookDao.count();
    }

    public List<Book> getAllBook() {
        return bookDao.getAllBook();
    }

    @Transactional
    public Book getBookByName(String name) {
        return bookDao.getBookByName(name);
    }

    @Transactional
    public String delateBook(String name) {
        return bookDao.delateBook(name);
    }
}
