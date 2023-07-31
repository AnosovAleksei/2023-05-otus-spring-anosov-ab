package ru.otus.dao;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;
import ru.otus.repository.AuthorRepository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class AuthorDaoJpa implements AuthorDao {

    private final AuthorRepository authorRepository;

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author getByName(String name) {
        return authorRepository.getByName(name).orElse(null);
    }

    @Override
    public Author create(Author author) {
        authorRepository.save(author);
        return author;
    }
}
