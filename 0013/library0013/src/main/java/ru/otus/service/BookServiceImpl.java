package ru.otus.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.repository.BookRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorService authorService;

    private final GenreService genreService;

    private final BookRepository bookRepository;


    @Override
    @Transactional
    public Book create(String name, String authorName, String genreName) {
        Author author = authorService.create(authorName);
        Genre genre = genreService.create(genreName);

        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setGenre(genre);

        bookRepository.save(book);
        return book;
    }

    @Override
    @Transactional
    public Book update(String name, String authorName, String genreName) {
        Author author = authorService.create(authorName);
        Genre genre = genreService.create(genreName);

        Book book = getByName(name);
        if (book != null) {
            throw new RuntimeException("book with bookName" + name + "does not exist");
        }
        book.setAuthor(author);
        book.setGenre(genre);


        return update(book);
    }

    @Override
    @Transactional
    public Book update(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public long count() {
        return bookRepository.count();
    }

    @Override
    @Transactional
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public Book getByName(String name) {
        Book book = bookRepository.getByName(name).orElse(null);
        if (book == null) {
            throw new RuntimeException("book with bookName" + name + "does not exist");
        }
        return book;
    }

    @Override
    @Transactional
    public Book getByID(Long bookId) {
        Book book = bookRepository.getById(bookId).orElse(null);
        if (book == null) {
            throw new RuntimeException("book with bookId" + bookId.toString() + "does not exist");
        }
        return book;
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
