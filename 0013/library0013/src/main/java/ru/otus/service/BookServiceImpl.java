package ru.otus.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Book;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentaryRepository;
import ru.otus.repository.GenreRepository;


import java.util.List;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final CommentaryRepository commentaryRepository;

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
    public Iterable<Book> getAll() {
        Iterable<Book> books = bookRepository.findAll();
        return books;
    }

    @Override
    public Book getByName(String name) {
        return bookRepository.getByName(name)
                .orElseThrow(() -> new NotFoundException("book with bookName" + name + "does not exist"));
    }

    @Override
    public Book getByID(Long bookId) {
        return bookRepository.getById(bookId)
                .orElseThrow(() -> new NotFoundException("book with bookId" + bookId.toString() + "does not exist"));
    }

    @Override
    @Transactional
    public void delete(String name) {
        Book book = bookRepository.getByName(name).orElse(null);
        if (book != null) {
            Long authorId = book.getAuthor().getId();
            Long genreId = book.getGenre().getId();
            Long bookId = book.getId();

            bookRepository.delete(book);
            {
                List<Book> tempBookList = bookRepository.getByAuthorId(authorId);
                if (tempBookList != null && tempBookList.size() == 0) {
                    authorRepository.delete(authorRepository.findById(authorId).get());
                }
            }
            {
                List<Book> tempBookList = bookRepository.getByGenreId(genreId);
                if (tempBookList != null && tempBookList.size() == 0) {
                    genreRepository.delete(genreRepository.findById(genreId).get());
                }
            }

            commentaryRepository.deleteAll(commentaryRepository.findByBookId(bookId));
        }
    }
}
