package ru.otus.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.service.AuthorService;
import ru.otus.service.BookConverter;
import ru.otus.service.BookService;
import ru.otus.service.GenreService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@ShellComponent
public class LibraryController {

    private final AuthorService authorService;

    private final GenreService genreService;

    private final BookService bookService;

    @ShellMethod(value = "getting count book in library", key = {"c", "count"})
    public int testingUser() {
        return bookService.count();
    }

    @ShellMethod(value = "getting authors", key = {"a", "authors"})
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthor();
    }

    @ShellMethod(value = "create", key = {"c_a", "create_author"})
    public Author createAuthor(String name) {
        return authorService.createAuthor(name);
    }

    @ShellMethod(value = "getting genrees", key = {"g", "genre"})
    public List<Genre> getAllGenre() {
        return genreService.getAllGenre();
    }

    @ShellMethod(value = "create", key = {"c_g", "create_genre"})
    public Genre createGenre(String name) {
        return genreService.createGenre(name);
    }


    @ShellMethod(value = "getting all book", key = {"b", "books"})
    public List<String> printAllBooks() {
        return new ArrayList<>() {
            {
                for (Book book : bookService.getAllBook()) {
                    add(BookConverter.convertBookToStr(book));
                }
            }
        };
    }

    @ShellMethod(value = "create book", key = {"c_b", "create_book"})
    public String printBook(String name, String author, String genre) {
        return BookConverter.convertBookToStr(bookService.createNewBook(name, author, genre));
    }

    @ShellMethod(value = "read book", key = {"r_b", "read_book"})
    public String readBook(String name) {
        return BookConverter.convertBookToStr(bookService.getBookByName(name));
    }

    @ShellMethod(value = "update book", key = {"u_b", "update_book"})
    public String updateBook(String name, String author, String genre) {
        return BookConverter.convertBookToStr(bookService.updateBook(name, author, genre));
    }

    @ShellMethod(value = "delate book", key = {"d_b", "delate_book"})
    public String delateBook(String name) {
        return bookService.delateBook(name);
    }
}
