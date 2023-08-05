package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    @Transactional
    public Author create(String name) {
        Author author = new Author(name);
        authorRepository.save(author);
        return author;
    }

    @Override
    public List<Author> getAll() {
        List<Author> authors = new ArrayList<>();
        authorRepository.findAll().forEach(authors::add);
        return authors;
    }
}
