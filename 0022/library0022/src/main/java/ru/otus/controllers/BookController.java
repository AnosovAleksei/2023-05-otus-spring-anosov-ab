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
        return bookRepository.findById(id).map(b -> new BookDto(b)).switchIfEmpty(Mono.error(
                new NotFoundException("author with id " + id + " does not exist")));
    }


    @PostMapping("/api/v1/book")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<BookDto> createBook(@Valid @RequestBody BookCreateDto bookCreateDto) {


        Mono<Author> author = authorRepository.findById(bookCreateDto.getAuthorId());
        Mono<Genre> genre = genreRepository.findById(bookCreateDto.getGenreId());

        author.switchIfEmpty(
                Mono.defer(() ->
                        Mono.error(
                                new NotFoundException(
                                        "author with id " + bookCreateDto.getAuthorId() + " does not exist"))));
        genre.switchIfEmpty(
                Mono.defer(() ->
                        Mono.error(
                                new NotFoundException(
                                        "genre with id " + bookCreateDto.getGenreId() + " does not exist"))));

        return bookRepository.save(new Book(bookCreateDto.getName(),
                        author.block(),
                        genre.block()))
                .map(b -> new BookDto(b));
    }

    @PutMapping("/api/v1/book/{id}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<BookDto> updateBook(@PathVariable String id,
                                    @Valid @RequestBody BookUpdateRequestDto bookUpdateRequestDto) {
        Mono<Author> author = authorRepository.findById(bookUpdateRequestDto.getAuthorId());
        Mono<Genre> genre = genreRepository.findById(bookUpdateRequestDto.getGenreId());
        Mono<Book> bookMono = bookRepository.findById(id);
        author.switchIfEmpty(
                Mono.defer(() ->
                        Mono.error(
                                new NotFoundException(
                                        "author with id " + bookUpdateRequestDto.getAuthorId() + " does not exist"))));
        genre.switchIfEmpty(
                Mono.defer(() ->
                        Mono.error(
                                new NotFoundException(
                                        "genre with id " + bookUpdateRequestDto.getGenreId() + " does not exist"))));
        bookMono.switchIfEmpty(
                Mono.defer(() ->
                        Mono.error(
                                new NotFoundException(
                                        "book with id " + id + " does not exist"))));
        Book book = bookMono.block();
        book.setName(bookUpdateRequestDto.getName());
        book.setAuthor(author.block());
        book.setGenre(genre.block());
        return bookRepository.save(book).map(bv -> new BookDto(bv));
    }

    @DeleteMapping("/api/v1/book/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable String id) {
        Mono<Book> bookMono = bookRepository.findById(id);
        bookMono.switchIfEmpty(
                Mono.defer(() ->
                        Mono.error(
                                new NotFoundException(
                                        "book with id " + id + " does not exist"))));
        Book book = bookMono.block();
        var ccc = bookRepository.delete(book).block();
    }
}
