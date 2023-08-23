package ru.otus.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.domain.Author;
import ru.otus.dto.AuthorCreateDto;
import ru.otus.dto.AuthorUpdateDto;
import ru.otus.service.AuthorService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/api/v1/author")
    public List<Author> getAuthors() {
        System.out.println("---------------------------");
        return authorService.getAll();
    }


    @GetMapping("/api/v1/author/{id}")
    public Author getAuthors(@PathVariable Long id) {
        System.out.println("++++++++");
        return authorService.read(id);
    }

    @PostMapping("/api/v1/author")
    public Author createAuthor(@Valid @RequestBody AuthorCreateDto authorCreateDto) {
        System.out.println(authorCreateDto);
        return authorService.create(authorCreateDto);
    }

    @PutMapping("/api/v1/author")
    public Author updateAuthor(@Valid @RequestBody AuthorUpdateDto authorUpdateDto) {
        System.out.println(authorUpdateDto);
        return authorService.update(authorUpdateDto);
    }

}
