package ru.otus.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
        return bookRepository.findById(id).map(b -> new BookDto(b)).switchIfEmpty(Mono.error(
                new NotFoundException("author with id " + id + " does not exist")));
    }




    @PostMapping("/api/v1/book")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<BookDto> createBook(@Valid @RequestBody BookCreateDto bookCreateDto) {
        Book book = new Book();
        book.setName(bookCreateDto.getName());
        return authorRepository.findById(bookCreateDto.getAuthorId())
                .switchIfEmpty(Mono.fromCallable(() -> {
                        throw new NotFoundException("Автор с указанным идентификатором " + bookCreateDto.getAuthorId()
                                + " не найден");
                })).map(author -> {book.setAuthor(author); return book;})
                .flatMap(b1->genreRepository.findById(bookCreateDto.getGenreId())
                        .switchIfEmpty(Mono.fromCallable(() -> {
                                throw new NotFoundException("Жанр с указанным идентификатором " +
                                        bookCreateDto.getGenreId() + " не найден");
                            })))
                .map(genre -> {book.setGenre(genre); return book;})
                .flatMap(bookRepository::save)
                        .map(b->new BookDto(b));


    }

    @PutMapping("/api/v1/book/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<BookDto> updateBook(@PathVariable String id,
                                    @Valid @RequestBody BookUpdateRequestDto bookUpdateRequestDto) {

        Book book = new Book();
        book.setName(bookUpdateRequestDto.getName());
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.fromCallable(() -> {
                    throw new NotFoundException("Книга с указанным идентификатором " + id
                            + " не найдена");
                })).map(b2 -> {book.setId(b2.getId()); return book;})
                .flatMap(b1->authorRepository.findById(bookUpdateRequestDto.getAuthorId())
                    .switchIfEmpty(Mono.fromCallable(() -> {
                        throw new NotFoundException("Автор с указанным идентификатором " + bookUpdateRequestDto.getAuthorId()
                            + " не найден");
                })).map(author -> {book.setAuthor(author); return book;}))
                .flatMap(b1->genreRepository.findById(bookUpdateRequestDto.getGenreId())
                        .switchIfEmpty(Mono.fromCallable(() -> {
                            throw new NotFoundException("Жанр с указанным идентификатором " +
                                    bookUpdateRequestDto.getGenreId() + " не найден");
                        })))
                .map(genre -> {book.setGenre(genre); return book;})
                .flatMap(bookRepository::save)
                .map(b->new BookDto(b));

    }

    @DeleteMapping("/api/v1/book/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable String id) {

        bookRepository.findById(id)
                .switchIfEmpty(Mono.fromCallable(() -> {
                    throw new NotFoundException("Книга с указанным идентификатором " + id
                            + " не найдена");
                }))
                .map(book->{log.info("delete book {}", book.getName()); return book;})
                .flatMap(bookRepository::delete).subscribe();
    }
}
