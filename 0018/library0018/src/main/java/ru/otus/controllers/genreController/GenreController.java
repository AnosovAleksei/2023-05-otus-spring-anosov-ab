package ru.otus.controllers.genreController;

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
import ru.otus.dto.GenreCreateDto;
import ru.otus.dto.GenreUpdateDto;
import ru.otus.service.GenreService;

@Validated
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
    public String createAuthor(Model model, @Valid GenreCreateDto genreCreateDto) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.GENRE_PAGE, true));
        model.addAttribute("genre", genreService.create(genreCreateDto));
        return NavigatorController.GENRE_PAGE;
    }

    @GetMapping("/genre")
    public String getAuthor(Model model, @Valid @NotNull @RequestParam("id") Long id) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.GENRE_PAGE, true));
        model.addAttribute("genre", genreService.read(id));
        return NavigatorController.GENRE_PAGE;
    }


    @PutMapping("/genre")
    public String updateAuthor(Model model, @Valid GenreUpdateDto genreUpdateDto) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.GENRE_PAGE, true));
        model.addAttribute("genre", genreService.update(genreUpdateDto));
        return NavigatorController.GENRE_PAGE;
    }
}
