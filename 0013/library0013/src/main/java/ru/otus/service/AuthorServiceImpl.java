package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.repository.AuthorRepository;

import java.util.List;


@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Author create(Author author) {
        author = authorRepository.getByName(author.getName()).orElse(author);
        if (author.getId() == null) {
            authorRepository.save(author);
        }
        return author;
    }

    @Override
    @Transactional
    public Author update(Author author) {
        authorRepository.findById(author.getId())
                .orElseThrow(() -> new NotFoundException("author with name" + author.getName() + "does not exist"));
        authorRepository.save(author);
        return author;
    }

    @Override
    public Author read(String name) {
        return authorRepository.getByName(name)
                .orElseThrow(() -> new NotFoundException("author with name" + name + "does not exist"));
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }
}
