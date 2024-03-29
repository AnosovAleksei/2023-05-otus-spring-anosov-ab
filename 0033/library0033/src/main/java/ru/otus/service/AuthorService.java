package ru.otus.service;

import ru.otus.domain.Author;
import ru.otus.dto.AuthorCreateDto;
import ru.otus.dto.AuthorUpdateDto;

import java.util.List;

public interface AuthorService {


    Author create(AuthorCreateDto authorCreateDto);


    Author update(AuthorUpdateDto authorUpdateDto);


    Author read(String name);


    Author read(Long id);



    List<Author> getAll();

}
