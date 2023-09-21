package ru.otus.megration.service;

import org.springframework.stereotype.Service;
import ru.otus.domain.Author;

@Service
public class AuthorService {
    public ru.otus.megration.entity.Author convert(Author author){
        System.out.println(author.getName());
        return new ru.otus.megration.entity.Author(author.getName());
    }

}
