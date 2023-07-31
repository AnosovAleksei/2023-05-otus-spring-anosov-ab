package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.repository.BookRepository;

import java.util.List;


@RequiredArgsConstructor
@Repository
public class BookDaoJpa implements BookDao {


    private final BookRepository bookRepository;

    @Override
    public long count() {
        return bookRepository.count();
    }


    @Override
    public void delete(String name) {
        Book book = getByName(name);
        if (book != null) {
            bookRepository.delete(book);
        }
    }

    @Override
    public Book upgrade(String name, Author author, Genre genre) {

        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setGenre(genre);

        bookRepository.save(book);

        return book;
    }

    @Override
    public Book update(Book book) {
        bookRepository.save(book);
        return book;
    }

    @Override
    public Book create(Book book) {
        bookRepository.save(book);
        return book;
    }


    @Override
    public Book getByName(String name) {
        var c = bookRepository.getByName(name).orElse(null);
        return bookRepository.getByName(name).orElse(null);
    }

    @Override
    public Book getById(Long bookId) {
        return bookRepository.getById(bookId).orElse(null);
    }


    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

}
