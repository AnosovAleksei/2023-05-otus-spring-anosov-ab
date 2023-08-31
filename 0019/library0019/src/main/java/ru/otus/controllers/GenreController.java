package ru.otus.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.domain.Genre;
import ru.otus.dto.GenreCreateDto;
import ru.otus.dto.GenreUpdateDto;
import ru.otus.dto.GenreUpdateRequestDto;
import ru.otus.service.GenreService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/api/v1/genre")
    public List<Genre> getGenre() {
        return genreService.getAll();
    }


    @GetMapping("/api/v1/genre/{id}")
    public Genre getGenre(@PathVariable Long id) {
        return genreService.read(id);
    }

    @PostMapping("/api/v1/genre")
    public Genre createGenre(@Valid @RequestBody GenreCreateDto genreCreateDto) {
        return genreService.create(genreCreateDto);
    }

    @PutMapping("/api/v1/genre/{id}")
    public Genre updateGenre(@PathVariable long id, @Valid @RequestBody GenreUpdateRequestDto genreUpdateRequestDto) {
        return genreService.update(new GenreUpdateDto(id, genreUpdateRequestDto.getName()));
    }

}
