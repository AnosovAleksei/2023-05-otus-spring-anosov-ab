package ru.otus.controllers.authorController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.controllers.NavigatorController;
import ru.otus.domain.Author;
import ru.otus.service.AuthorService;

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
    public String createAuthor(Model model, String name, HttpServletRequest request) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.AUTHOR_PAGE, true));
        Author author = new Author();
        author.setName(name);

        model.addAttribute("author", authorService.create(author));
        return NavigatorController.AUTHOR_PAGE;
    }

    @GetMapping("/author")
    public String getAuthor(Model model, @RequestParam("id") Long id) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.AUTHOR_PAGE, true));
        model.addAttribute("author", authorService.read(id));
        return NavigatorController.AUTHOR_PAGE;
    }


    @PutMapping("/author")
    public String updateAuthor(Model model, AuthorUpdateRequest authorUpdateRequest) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.AUTHOR_PAGE, true));
        Author author = new Author();
        author.setName(authorUpdateRequest.getName());
        author.setId(authorUpdateRequest.getId());

        model.addAttribute("author", authorService.update(author));
        return NavigatorController.AUTHOR_PAGE;
    }
}
