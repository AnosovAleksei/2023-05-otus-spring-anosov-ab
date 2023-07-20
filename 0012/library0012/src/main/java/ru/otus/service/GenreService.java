package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Genre;

import java.util.List;


@RequiredArgsConstructor
@Service
public class GenreService {

    private final GenreDao genreDao;

    public Genre createGenre(String name) {
        Genre genre = genreDao.getGenreByName(name);
        if (genre != null) {
            return genre;
        }
        return genreDao.createGenre(name);
    }


    public List<Genre> getAllGenre() {
        return genreDao.getAllGenre();
    }
}
