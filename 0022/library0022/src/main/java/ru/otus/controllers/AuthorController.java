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
import ru.otus.domain.Author;
import ru.otus.dto.AuthorCreateDto;
import ru.otus.dto.AuthorUpdateRequestDto;
import ru.otus.exeption.AppWorkException;
import ru.otus.exeption.NotFoundException;
import ru.otus.repository.AuthorRepository;


@RequiredArgsConstructor
@RestController
public class AuthorController {


    private final AuthorRepository authorRepository;


    @GetMapping("/api/v1/author")
    public Flux<Author> getAuthors() {
        return authorRepository.findAll();
    }


    @GetMapping("/api/v1/author/{id}")
    public Mono<Author> getAuthors(@PathVariable String id) {
        return authorRepository.findById(id);
    }


    @PostMapping("/api/v1/author")
    public Mono<Author> createAuthor(@Valid @RequestBody AuthorCreateDto authorCreateDto) {
        if (authorRepository.findByName(authorCreateDto.getName()).block() != null) {
            throw new AppWorkException("author with name " + authorCreateDto.getName() + " already exists");
        }
        return authorRepository.save(new Author(authorCreateDto.getName()));
    }


    @PutMapping("/api/v1/author/{id}")
    public Mono<Author> updateAuthor(@PathVariable String id,
                                     @Valid @RequestBody AuthorUpdateRequestDto authorUpdateRequestDto) {

        return authorRepository.findById(id)
                .mapNotNull(a -> {
                    a.setName(authorUpdateRequestDto.getName());
                    return authorRepository.save(a);
                })
                .switchIfEmpty(Mono.error(new NotFoundException("author with id " + id + " does not exist"))).block();
    }
}
