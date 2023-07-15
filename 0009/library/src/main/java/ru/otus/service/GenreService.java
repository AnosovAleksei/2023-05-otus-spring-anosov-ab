package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Genre;

import java.util.List;


@RequiredArgsConstructor
@Component
public class GenreService {

    private final GenreDao genreDao;

    public Genre createGenre(String name) {
        Genre genre = genreDao.getGenreByName(name);
        if (genre != null) {
            return genre;
        }
        genreDao.createGenre(name);
        genre = genreDao.getGenreByName(name);
        //if(author!=null){
        //TODO add checking what was created;
        //}
        return genre;
    }


    public List<Genre> getAllGenre() {
        return genreDao.getAllGenre();
    }
}
