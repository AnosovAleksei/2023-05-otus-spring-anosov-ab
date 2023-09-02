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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.BookCreateDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.BookUpdateRequestDto;
import ru.otus.exeption.NotFoundException;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    @GetMapping("/api/v1/book")
    public Flux<BookDto> getBookss() {
        return bookRepository.findAll().map(b -> new BookDto(b));
    }


    @GetMapping("/api/v1/book/{id}")
    public Mono<BookDto> getBook(@PathVariable String id) {
        return bookRepository.findById(id).map(b -> new BookDto(b));
    }


    @PostMapping("/api/v1/book")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<BookDto> createBook(@Valid @RequestBody BookCreateDto bookCreateDto) {

        Author author = authorRepository.findById(bookCreateDto.getAuthorId()).block();
        if (author == null) {
            throw new NotFoundException("author with id " + bookCreateDto.getAuthorId() + " does not exist");
        }


        Genre genre = genreRepository.findById(bookCreateDto.getGenreId()).block();
        if (genre == null) {
            throw new NotFoundException("genre with id " + bookCreateDto.getGenreId() + " does not exist");
        }

        return bookRepository.save(new Book(bookCreateDto.getName(), author, genre)).map(b -> new BookDto(b));
    }

    @PutMapping("/api/v1/book/{id}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<BookDto> updateBook(@PathVariable String id,
                                    @Valid @RequestBody BookUpdateRequestDto bookUpdateRequestDto) {

        Author author = authorRepository.findById(bookUpdateRequestDto.getAuthorId()).block();
        if (author == null) {
            throw new NotFoundException("author with id " + bookUpdateRequestDto.getAuthorId() + " does not exist");
        }


        Genre genre = genreRepository.findById(bookUpdateRequestDto.getGenreId()).block();
        if (genre == null) {
            throw new NotFoundException("genre with id " + bookUpdateRequestDto.getGenreId() + " does not exist");
        }

        return bookRepository.findById(id)
                .mapNotNull(a -> {
                    a.setName(bookUpdateRequestDto.getName());
                    a.setAuthor(author);
                    a.setGenre(genre);
                    return bookRepository.save(a).map(b -> new BookDto(b));
                })
                .switchIfEmpty(Mono.error(new NotFoundException("book with id " + id + " does not exist"))).block();
    }

    @DeleteMapping("/api/v1/book/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable String id) {
        Book book = bookRepository.findById(id).block();
        if (book == null) {
            throw new NotFoundException("book with id " + id + " does not exist");
        }
        bookRepository.delete(book).block();
    }
}
