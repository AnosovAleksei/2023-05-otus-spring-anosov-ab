package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Genre;

import java.util.List;


@RequiredArgsConstructor
@Service
public class GenreService {

    private final GenreDao genreDao;

    @Transactional
    public Genre create(String name) {
        return genreDao.create(new Genre(name));
    }


    public List<Genre> getAll() {
        return genreDao.getAll();
    }
}
