package ru.otus.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.BookCreateDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.BookUpdateDto;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentaryRepository;
import ru.otus.repository.GenreRepository;


import java.util.ArrayList;
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
    public Book create(BookCreateDto bookCreateDto) {

        Author author = authorRepository.findById(bookCreateDto.getAuthorId())
                .orElseThrow(() ->
                        new NotFoundException("author with name" + bookCreateDto.getAuthorId() + "does not exist"));

        Genre genre = genreRepository.findById(bookCreateDto.getGenreId())
                .orElseThrow(() ->
                        new NotFoundException("genre with id " + bookCreateDto.getGenreId() + " does not exist"));

        Book book = new Book();

        book.setName(bookCreateDto.getName());
        book.setAuthor(author);
        book.setGenre(genre);

        return create(book);
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
    public Book update(BookUpdateDto bookUpdateDto) {
        Author author = authorRepository.findById(bookUpdateDto.getAuthorId())
                .orElseThrow(() ->
                        new NotFoundException("author with name" + bookUpdateDto.getAuthorId() + "does not exist"));

        Genre genre = genreRepository.findById(bookUpdateDto.getGenreId())
                .orElseThrow(() ->
                        new NotFoundException("genre with id " + bookUpdateDto.getGenreId() + " does not exist"));

        Book book = bookRepository.getById(bookUpdateDto.getBookId())
                .orElseThrow(() ->
                        new NotFoundException("book with bookId " + bookUpdateDto.getBookId() + " does not exist"));

        book.setName(bookUpdateDto.getName());
        book.setAuthor(author);
        book.setGenre(genre);

        return update(book);
    }

    @Override
    public long count() {
        return bookRepository.count();
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
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
        Book book = bookRepository.getByName(name)
                .orElseThrow(() -> new NotFoundException("book with bookName" + name + "does not exist"));
        bookRepository.delete(book);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Book book = bookRepository.getById(id)
                .orElseThrow(() -> new NotFoundException("book with bookId" + id + "does not exist"));
        bookRepository.delete(book);
    }

    @Override
    public BookDto converterToBookDto(Book book) {
        return new BookDto(book.getId(), book.getName(), book.getAuthor().getName(), book.getGenre().getName());
    }

    @Override
    public List<BookDto> converterToListBookDto(List<Book> books) {
        return new ArrayList<>() {{
            for (Book book : books) {
                add(new BookDto(book.getId(), book.getName(), book.getAuthor().getName(), book.getGenre().getName()));
            }
        }};
    }
}
