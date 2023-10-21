package ru.otus.service;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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


    @HystrixCommand(commandKey = "getRentsKey")
    @Override
    @Transactional
    public BookDto create(BookCreateDto bookCreateDto) {

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

        bookRepository.save(book);
        return converterToBookDto(book);
    }



    @HystrixCommand(commandKey = "getRentsKey")
    @Override
    @Transactional
    public BookDto update(BookUpdateDto bookUpdateDto) {
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
        bookRepository.save(book);
        return converterToBookDto(book);
    }

    @HystrixCommand(commandKey = "getRentsKey")
    @Override
    @Transactional(readOnly = true)
    public long count() {
        return bookRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAll() {
        return converterToListBookDto(bookRepository.findAll());
    }

    @HystrixCommand(commandKey = "getRentsKey")
    @Override
    @Transactional(readOnly = true)
    public BookDto getByName(String name) {
        return converterToBookDto(bookRepository.getByName(name)
                .orElseThrow(() -> new NotFoundException("book with bookName" + name + "does not exist")));
    }

    @HystrixCommand(commandKey = "getRentsKey")
    @Override
    @Transactional(readOnly = true)
    public BookDto getById(Long bookId) {
        return converterToBookDto(bookRepository.getById(bookId)
                .orElseThrow(() -> new NotFoundException("book with bookId" + bookId.toString() + "does not exist")));
    }

    @HystrixCommand(commandKey = "getRentsKey")
    @Override
    @Transactional
    public void delete(String name) {
        Book book = bookRepository.getByName(name)
                .orElseThrow(() -> new NotFoundException("book with bookName" + name + "does not exist"));
        bookRepository.delete(book);
    }

    @HystrixCommand(commandKey = "getRentsKey")
    @Override
    @Transactional
    public void delete(Long id) {
        Book book = bookRepository.getById(id)
                .orElseThrow(() -> new NotFoundException("book with bookId" + id + "does not exist"));
        bookRepository.delete(book);
    }




    public BookDto converterToBookDto(Book book) {
        return new BookDto(book.getId(), book.getName(), book.getAuthor().getName(), book.getGenre().getName());
    }


    public List<BookDto> converterToListBookDto(List<Book> books) {
        return new ArrayList<>() {{
            for (Book book : books) {
                add(new BookDto(book.getId(), book.getName(), book.getAuthor().getName(), book.getGenre().getName()));
            }
        }};
    }
}
