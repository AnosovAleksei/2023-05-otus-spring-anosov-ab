package ru.otus.dao;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Genre;
import ru.otus.repository.GenreRepository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class GenreDaoJpa implements GenreDao {

    private final GenreRepository genreRepository;

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre getByName(String name) {
        return genreRepository.getByName(name).orElse(null);
    }


    @Override
    public Genre create(Genre genre) {
        genreRepository.save(genre);
        return genre;
    }
}
