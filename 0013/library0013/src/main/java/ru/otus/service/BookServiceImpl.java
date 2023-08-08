package ru.otus.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    @Override
    public Book create(Book book) {
        authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(() ->
                        new NotFoundException("author with bookName" + book.getAuthor().getName() + "does not exist"));

        genreRepository.findById(book.getGenre().getId())
                .orElseThrow(() ->
                        new NotFoundException("genre with bookName" + book.getGenre().getName() + "does not exist"));

        bookRepository.save(book);
        return book;
    }

    @Override
    @Transactional
    public Book update(Book book) {
        Book book1 = getByName(book.getName());
        if (book1 == null) {
            throw new NotFoundException("book with bookName " + book.getName() + " does not exist");
        }
        authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(() ->
                        new NotFoundException("author with bookName" + book.getAuthor().getName() + "does not exist"));

        genreRepository.findById(book.getGenre().getId())
                .orElseThrow(() ->
                        new NotFoundException("genre with bookName" + book.getGenre().getName() + "does not exist"));


        bookRepository.save(book);
        return book;
    }

    @Override
    public long count() {
        return bookRepository.count();
    }

    @Override
    @Transactional
    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        bookRepository.findAll().forEach(books::add);

        List<Author> authors = new ArrayList<>();
        authorRepository.findAll().forEach(authors::add);

        List<Genre> genries = new ArrayList<>();
        genreRepository.findAll().forEach(genries::add);
        return books;
    }

    @Override
    @Transactional
    public Book getByName(String name) {
        return bookRepository.getByName(name)
                .orElseThrow(() -> new NotFoundException("book with bookName" + name + "does not exist"));
    }

    @Override
    @Transactional
    public Book getByID(Long bookId) {
        return bookRepository.getById(bookId)
                .orElseThrow(() -> new NotFoundException("book with bookId" + bookId.toString() + "does not exist"));
    }

    @Override
    @Transactional
    public void delete(String name) {
        Book book = bookRepository.getByName(name).orElse(null);
        if (book != null) {
            bookRepository.delete(book);
        }
    }
}
