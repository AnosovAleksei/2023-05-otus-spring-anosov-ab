package ru.otus.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Commentary;
import ru.otus.domain.Genre;
import ru.otus.service.AuthorService;
import ru.otus.service.CommentaryService;
import ru.otus.service.ModelConverter;
import ru.otus.service.BookService;
import ru.otus.service.GenreService;

import java.util.List;

@RequiredArgsConstructor
@ShellComponent
public class LibraryController {

    private final AuthorService authorService;

    private final GenreService genreService;

    private final BookService bookService;


    private final CommentaryService commentaryService;

    @ShellMethod(value = "getting count book in library", key = {"c", "count"})
    public long testingUser() {
        return bookService.count();
    }

    @ShellMethod(value = "getting authors", key = {"a", "authors"})
    public Iterable<Author> getAllAuthors() {
        return authorService.getAll();
    }

    @ShellMethod(value = "create", key = {"c_a", "create_author"})
    public Author createAuthor(String name) {
        return authorService.create(name);
    }

    @ShellMethod(value = "getting genrees", key = {"g", "genre"})
    public Iterable<Genre> getAllGenre() {
        return genreService.getAll();
    }

    @ShellMethod(value = "create", key = {"c_g", "create_genre"})
    public Genre createGenre(String name) {
        return genreService.create(name);
    }

    @Transactional(readOnly = true)
    @ShellMethod(value = "getting all book", key = {"b", "books"})
    public List<String> printAllBooks() {
        authorService.getAll();
        genreService.getAll();
        return ModelConverter.allBookDescription(bookService.getAll());
    }

    @ShellMethod(value = "create book", key = {"c_b", "create_book"})
    public String createBook(String name, String authorName, String genreName) {

        Author author = authorService.create(authorName);
        Genre genre = genreService.create(genreName);
        Book book = new Book();
        book.setGenre(genre);
        book.setAuthor(author);
        book.setName(name);


        return ModelConverter.convertBookToStr(bookService.create(book));
    }

    @ShellMethod(value = "read book", key = {"r_b", "read_book"})
    public String readBook(String name) {
        return ModelConverter.convertBookToStr(bookService.getByName(name));
    }

    @ShellMethod(value = "update book", key = {"u_b", "update_book"})
    public String updateBook(String name, String authorName, String genreName) {
        Author author = authorService.create(authorName);
        Genre genre = genreService.create(genreName);
        Book book = bookService.getByName(name);
        book.setGenre(genre);
        book.setAuthor(author);



        return ModelConverter.convertBookToStr(bookService.update(book));
    }

    @ShellMethod(value = "delate book", key = {"d_b", "delate_book"})
    public String delateBook(String name) {
        Book book = bookService.getByName(name);
        String rez = ModelConverter.operationDelateBook(book);
        bookService.delete(name);
        return rez;
    }

    @ShellMethod(value = "get all commentary", key = {"cm", "commentary"})
    public List<String> getAllCommentary() {
        return commentaryService.getAllForString();
    }

    @ShellMethod(value = "create commentary", key = {"c_cm", "create_commentary"})
    public String createComment(Long bookId, String messege) {
        return ModelConverter.convertComentaryToStr(commentaryService.create(bookId, messege));
    }

    @ShellMethod(value = "read commentary", key = {"r_cm", "read_commentary"})
    public String readComment(Long commentaryId) {
        return ModelConverter.convertComentaryToStr(commentaryService.read(commentaryId));
    }

    @ShellMethod(value = "update commentary", key = {"u_cm", "update_commentary"})
    public String updateComment(Long commentaryId, String msg, Long bookId) {
        Book book = bookService.getByID(bookId);
        Commentary commentary = commentaryService.read(commentaryId);
        commentary.setMessage(msg);
        commentary.setBookId(book.getId());
        return ModelConverter.convertComentaryToStr(commentaryService.update(commentary));
    }

    @ShellMethod(value = "delate commentary", key = {"d_cm", "delate_commentary"})
    public String delateComment(Long commentaryId) {
        Commentary commentary = commentaryService.read(commentaryId);
        String rez = ModelConverter.operationDelateCommentary(commentary);
        commentaryService.delete(commentary);
        return rez;
    }
}
