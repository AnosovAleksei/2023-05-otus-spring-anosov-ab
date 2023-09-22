package ru.otus.migration.service;

import org.springframework.stereotype.Service;
import ru.otus.domain.Author;

@Service
public class AuthorService {
    public ru.otus.migration.entity.Author convert(Author author) {
        return new ru.otus.migration.entity.Author(author.getName());
    }

}
