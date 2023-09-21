package ru.otus.megration.service;

import org.springframework.stereotype.Service;
import ru.otus.domain.Genre;

@Service
public class GenreService {
    public ru.otus.megration.entity.Genre convert(Genre genre){
        System.out.println(genre.getName());
        return new ru.otus.megration.entity.Genre(genre.getName());
    }

}
