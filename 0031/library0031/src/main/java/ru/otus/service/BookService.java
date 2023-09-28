package ru.otus.service;

import ru.otus.dto.BookCreateDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.BookUpdateDto;

import java.util.List;


public interface BookService {

    BookDto create(BookCreateDto bookCreateDto);

    BookDto update(BookUpdateDto bookUpdateDto);

    long count();

    List<BookDto> getAll();

    BookDto getByName(String name);

    BookDto getById(Long bookId);

    void delete(String name);

    void delete(Long id);




}
