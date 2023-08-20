package ru.otus.controllers.bookController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.controllers.NavigatorController;
import ru.otus.dto.BookCreateDto;
import ru.otus.dto.BookDeleteDto;
import ru.otus.dto.BookUpdateDto;
import ru.otus.service.BookService;


@Validated
@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;



    @GetMapping("/books")
    public String getBooks(Model model) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.BOOK_PAGE, true));
        model.addAttribute("books", bookService.getAll());
        return NavigatorController.BOOK_PAGE;
    }

    @PostMapping("/book")
    public String createBook(Model model, @Valid BookCreateDto bookCreateDto) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.BOOK_PAGE, true));
        model.addAttribute("book", bookService.create(bookCreateDto));
        return NavigatorController.BOOK_PAGE;
    }

    @GetMapping("/book")
    public String getBook(Model model, @Valid @NotNull @RequestParam("id") Long id) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.BOOK_PAGE, true));
        model.addAttribute("book", bookService.getByID(id));
        return NavigatorController.BOOK_PAGE;
    }

    @PutMapping("/book")
    public String updateBook(Model model, @Valid BookUpdateDto bookUpdateDto) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.BOOK_PAGE, true));
        model.addAttribute("book", bookService.update(bookUpdateDto));

        return NavigatorController.BOOK_PAGE;
    }

    @DeleteMapping("/book")
    public String deleteBook(Model model, @Valid BookDeleteDto bookDeleteDto) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.BOOK_PAGE, true));

        bookService.delete(bookDeleteDto.getId());

        model.addAttribute("bookDelete", "книга удалена");
        return NavigatorController.BOOK_PAGE;
    }
}
