package ru.otus.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.dao.BookDaoJdbc;
import ru.otus.dto.Author;

import java.util.List;

@RequiredArgsConstructor
@ShellComponent
public class LibraryController {

    private final BookDaoJdbc bookDaoJdbc;
    @ShellMethod(value = "getting count book in library", key = {"c", "count"})
    public int testingUser (){
        return bookDaoJdbc.count();
    }

    @ShellMethod(value = "getting authors", key = {"a", "authors"})
    public List<Author> printAuthors (){
        return bookDaoJdbc.getAllAuthor();
    }
}
