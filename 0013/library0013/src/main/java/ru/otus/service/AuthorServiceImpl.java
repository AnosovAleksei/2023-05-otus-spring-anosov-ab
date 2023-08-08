package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.repository.AuthorRepository;



@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    @Transactional
    public Author create(String name) {
        Author author = authorRepository.getByName(name).orElse(new Author(name));
        if (author.getId() == null) {
            authorRepository.save(author);
        }
        return author;
    }


    @Override
    public Iterable<Author> getAll() {
        return authorRepository.findAll();
    }
}
