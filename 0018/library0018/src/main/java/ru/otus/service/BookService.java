package ru.otus.service;

import ru.otus.domain.Book;
import ru.otus.dto.BookCreateDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.BookUpdateDto;

import java.util.List;


public interface BookService {

    Book create(Book book);

    Book create(BookCreateDto bookCreateDto);

    Book update(Book book);

    Book update(BookUpdateDto bookUpdateDto);

    long count();

    List<Book> getAll();

    Book getByName(String name);

    Book getByID(Long bookId);

    void delete(String name);

    void delete(Long id);


    BookDto converterToBookDto(Book book);

    List<BookDto> converterToListBookDto(List<Book> books);


}
