package ru.otus.controllers.genreController;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.controllers.NavigatorController;
import ru.otus.domain.Genre;
import ru.otus.service.GenreService;

@RequiredArgsConstructor
@Controller
public class GenreController {

    private final GenreService genreService;


    @GetMapping("/genries")
    public String getGenries(Model model) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.GENRE_PAGE, true));
        model.addAttribute("genries", genreService.getAll());
        return NavigatorController.GENRE_PAGE;
    }

    @PostMapping("/genre")
    public String createAuthor(Model model, String name) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.GENRE_PAGE, true));
        Genre genre = new Genre();
        genre.setName(name);

        model.addAttribute("genre", genreService.create(genre));
        return NavigatorController.GENRE_PAGE;
    }

    @GetMapping("/genre")
    public String getAuthor(Model model, @RequestParam("id") Long id) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.GENRE_PAGE, true));
        model.addAttribute("genre", genreService.read(id));
        return NavigatorController.GENRE_PAGE;
    }


    @PutMapping("/genre")
    public String updateAuthor(Model model, GenreUpdateRequest genreUpdateRequest) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.GENRE_PAGE, true));
        Genre genre = new Genre();
        genre.setName(genreUpdateRequest.getName());
        genre.setId(genreUpdateRequest.getId());

        model.addAttribute("genre", genreService.update(genre));
        return NavigatorController.GENRE_PAGE;
    }
}
