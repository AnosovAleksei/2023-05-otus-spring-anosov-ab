package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Genre;
import ru.otus.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    @Transactional
    public Genre create(String name) {
        Genre genre = genreRepository.getByName(name).orElse(new Genre(name));
        if (genre.getId() == null) {
            genreRepository.save(genre);
        }
        return genre;
    }


    @Override
    public List<Genre> getAll() {
        List<Genre> genries = new ArrayList<>();
        genreRepository.findAll().forEach(genries::add);
        return genries;
    }
}
