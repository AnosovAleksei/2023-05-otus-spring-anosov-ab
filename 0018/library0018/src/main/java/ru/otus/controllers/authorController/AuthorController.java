package ru.otus.controllers.authorController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.controllers.NavigatorController;
import ru.otus.dto.AuthorCreateDto;
import ru.otus.dto.AuthorUpdateDto;
import ru.otus.service.AuthorService;

@Validated
@RequiredArgsConstructor
@Controller
public class AuthorController {

    private final AuthorService authorService;


    @GetMapping("/authors")
    public String getAuthors(Model model) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.AUTHOR_PAGE, true));
        model.addAttribute("authors", authorService.getAll());
        return NavigatorController.AUTHOR_PAGE;
    }

    @PostMapping("/author")
    public String createAuthor(@Valid AuthorCreateDto authorCreateDto, Model model) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.AUTHOR_PAGE, true));
        model.addAttribute("author", authorService.create(authorCreateDto));
        return NavigatorController.AUTHOR_PAGE;
    }

    @GetMapping("/author")
    public String getAuthor(Model model, @Valid @NotNull @RequestParam("id") Long id) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.AUTHOR_PAGE, true));
        model.addAttribute("author", authorService.read(id));
        return NavigatorController.AUTHOR_PAGE;
    }


    @PutMapping("/author")
    public String updateAuthor(Model model, @Valid AuthorUpdateDto authorUpdateDto) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.AUTHOR_PAGE, true));
        model.addAttribute("author", authorService.update(authorUpdateDto));
        return NavigatorController.AUTHOR_PAGE;
    }
}
