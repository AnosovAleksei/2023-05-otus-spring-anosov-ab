package ru.otus.controllers.bookController;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.controllers.NavigatorController;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.service.AuthorService;
import ru.otus.service.BookService;
import ru.otus.service.GenreService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @GetMapping("/books")
    public String getBooks(Model model) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.BOOK_PAGE, true));

        List<BookDto> books = new ArrayList<>() {{
            for (Book book : bookService.getAll()) {
                add(new BookDto(book.getId(), book.getName(), book.getAuthor().getName(), book.getGenre().getName()));
            }
        }};
        model.addAttribute("books", books);
        return NavigatorController.BOOK_PAGE;
    }

    @PostMapping("/book")
    public String createBook(Model model, BookCreateRequest bookCreateRequest) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.BOOK_PAGE, true));


        Author author = authorService.read(bookCreateRequest.getAuthorId());

        Genre genre = genreService.read(bookCreateRequest.getGenreId());

        Book book = new Book();

        book.setName(bookCreateRequest.getName());
        book.setAuthor(author);
        book.setGenre(genre);

        book = bookService.create(book);
        BookDto bookDto = new BookDto(book.getId(),
                book.getName(), book.getAuthor().getName(), book.getGenre().getName());
        model.addAttribute("book", bookDto);
        return NavigatorController.BOOK_PAGE;
    }

    @GetMapping("/book")
    public String getBook(Model model, @RequestParam("id") Long id) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.BOOK_PAGE, true));

        Book book = bookService.getByID(id);

        BookDto bookDto = new BookDto(book.getId(),
                book.getName(), book.getAuthor().getName(), book.getGenre().getName());
        model.addAttribute("book", bookDto);
        return NavigatorController.BOOK_PAGE;
    }

    @PutMapping("/book")
    public String updateBook(Model model, BookUpdateRequest bookUpdateRequest) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.BOOK_PAGE, true));
        Author author = authorService.read(bookUpdateRequest.getAuthorId());

        Genre genre = genreService.read(bookUpdateRequest.getGenreId());

        Book book = bookService.getByID(bookUpdateRequest.getBookId());

        book.setName(bookUpdateRequest.getName());
        book.setAuthor(author);
        book.setGenre(genre);

        book = bookService.update(book);

        BookDto bookDto =
                new BookDto(book.getId(), book.getName(), book.getAuthor().getName(), book.getGenre().getName());
        model.addAttribute("book", bookDto);

        return NavigatorController.BOOK_PAGE;
    }

    @DeleteMapping("/book")
    public String deleteBook(Model model, Long id) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.BOOK_PAGE, true));

        bookService.delete(id);

        model.addAttribute("bookDelete", "книга удалена");
        return NavigatorController.BOOK_PAGE;
    }
}
