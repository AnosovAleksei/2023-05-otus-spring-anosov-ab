package ru.otus.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.dto.BookCreateDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.BookUpdateDto;
import ru.otus.dto.BookUpdateRequestDto;
import ru.otus.service.BookService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;

    @GetMapping("/api/v1/book")
    public List<BookDto> getBookss() {
        return bookService.getAll();
    }


    @GetMapping("/api/v1/book/{id}")
    public BookDto getBook(@PathVariable Long id) {
        id = id;
        return bookService.getById(id);
    }

    @PostMapping("/api/v1/book")
    @ResponseStatus(code = HttpStatus.CREATED)
    public BookDto createBook(@Valid @RequestBody BookCreateDto bookCreateDto) {
        return bookService.create(bookCreateDto);
    }

    @PutMapping("/api/v1/book/{id}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public BookDto updateBook(@PathVariable long id, @Valid @RequestBody BookUpdateRequestDto bookUpdateRequestDto) {
        return bookService.update(new BookUpdateDto(id,
                                                    bookUpdateRequestDto.getName(),
                                                    bookUpdateRequestDto.getAuthorId(),
                                                    bookUpdateRequestDto.getGenreId()));
    }

    @DeleteMapping("/api/v1/book/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable long id) {
        bookService.delete(id);
    }
}
