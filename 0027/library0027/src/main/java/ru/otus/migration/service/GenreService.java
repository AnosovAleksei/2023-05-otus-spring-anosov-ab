package ru.otus.migration.service;

import org.springframework.stereotype.Service;
import ru.otus.domain.Genre;

@Service
public class GenreService {
    public ru.otus.migration.entity.Genre convert(Genre genre) {
        return new ru.otus.migration.entity.Genre(genre.getName());
    }

}
