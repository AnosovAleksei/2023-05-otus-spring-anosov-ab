package ru.otus.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public BookDto createBook(@Valid @RequestBody BookCreateDto bookCreateDto) {
        return bookService.create(bookCreateDto);
    }

    @PutMapping("/api/v1/book/{id}")
    public BookDto updateBook(@PathVariable long id, @Valid @RequestBody BookUpdateRequestDto bookUpdateRequestDto) {
        return bookService.update(new BookUpdateDto(id,
                                                    bookUpdateRequestDto.getName(),
                                                    bookUpdateRequestDto.getAuthorId(),
                                                    bookUpdateRequestDto.getGenreId()));
    }

    @DeleteMapping("/api/v1/book/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable long id) {
        bookService.delete(id);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
