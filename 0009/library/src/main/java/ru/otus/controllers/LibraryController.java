package ru.otus.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.dao.AuthorDaoJdbc;
import ru.otus.dao.BookDaoJdbc;
import ru.otus.dao.GenreDaoJdbc;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;

@RequiredArgsConstructor
@ShellComponent
public class LibraryController {

    private final BookDaoJdbc bookDaoJdbc;

    private final AuthorDaoJdbc authorDaoJdbc;

    private final GenreDaoJdbc genreDaoJdbc;

    @ShellMethod(value = "getting count book in library", key = {"c", "count"})
    public int testingUser() {
        return bookDaoJdbc.count();
    }

    @ShellMethod(value = "getting authors", key = {"a", "authors"})
    public List<Author> getAllAuthors() {
        return authorDaoJdbc.getAllAuthor();
    }

    @ShellMethod(value = "create", key = {"c_a", "create_author"})
    public Author createAuthor(String name) {
        return authorDaoJdbc.createAuthor(name);
    }

    @ShellMethod(value = "getting genrees", key = {"g", "genre"})
    public List<Genre> getAllGenre() {
        return genreDaoJdbc.getAllGenre();
    }

    @ShellMethod(value = "create", key = {"c_g", "create_genre"})
    public Genre createGenre(String name) {
        return genreDaoJdbc.createGenre(name);
    }


    @ShellMethod(value = "getting all book", key = {"b", "books"})
    public List<Book> printAllBooks() {
        return bookDaoJdbc.getAllBook();
    }

    @ShellMethod(value = "create book", key = {"c_b", "create_book"})
    public Book printBook(String name, String author, String genre) {
        return bookDaoJdbc.createNewBook(name, author, genre);
    }

    @ShellMethod(value = "read book", key = {"r_b", "read_book"})
    public Book readBook(String name) {
        return bookDaoJdbc.getBookByName(name);
    }

    @ShellMethod(value = "update book", key = {"u_b", "update_book"})
    public Book updateBook(String name, String author, String genre) {
        return bookDaoJdbc.updateBook(name, author, genre);
    }

    @ShellMethod(value = "delate book", key = {"d_b", "delate_book"})
    public String delateBook(String name) {
        return bookDaoJdbc.delateBook(name);
    }
}
