package ru.otus.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Genre;
import ru.otus.dto.GenreCreateDto;
import ru.otus.dto.GenreUpdateRequestDto;
import ru.otus.exeption.AppWorkException;
import ru.otus.exeption.NotFoundException;
import ru.otus.repository.GenreRepository;

@RequiredArgsConstructor
@RestController
public class GenreController {

    private final GenreRepository genreRepository;

    @GetMapping("/api/v1/genre")
    public Flux<Genre> getGenre() {
        return genreRepository.findAll();
    }


    @GetMapping("/api/v1/genre/{id}")
    public Mono<Genre> getGenre(@PathVariable String id) {
        return genreRepository.findById(id);
    }

    @PostMapping("/api/v1/genre")
    public Mono<Genre> createGenre(@Valid @RequestBody GenreCreateDto genreCreateDto) {
        if (genreRepository.findByName(genreCreateDto.getName()).block() != null) {
            throw new AppWorkException("genre with name " + genreCreateDto.getName() + " already exists");
        }
        return genreRepository.save(new Genre(genreCreateDto.getName()));
    }

    @PutMapping("/api/v1/genre/{id}")
    public Mono<Genre> updateGenre(@PathVariable String id,
                                   @Valid @RequestBody GenreUpdateRequestDto genreUpdateRequestDto) {
        return genreRepository.findById(id)
                .mapNotNull(a -> {
                    a.setName(genreUpdateRequestDto.getName());
                    return genreRepository.save(a);
                })
                .switchIfEmpty(Mono.error(new NotFoundException("genre with id " + id + " does not exist"))).block();
    }

}
