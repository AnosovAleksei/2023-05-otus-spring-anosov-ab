package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Genre;
import ru.otus.repository.GenreRepository;

import java.util.List;


@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    //    @Override
//    @Transactional
//    public Genre create(String name) {
//        Genre genre = genreRepository.getByName(name).orElse(new Genre(name));
//        if (genre.getId() == null) {
//            genreRepository.save(genre);
//        }
//        return genre;
//    }
    @Override
    public Genre create(Genre genre) {
        genre = genreRepository.getByName(genre.getName()).orElse(genre);
        if (genre.getId() == null) {
            genreRepository.save(genre);
        }
        return genre;
    }

    @Override
    @Transactional
    public Genre update(Genre genre) {
        genreRepository.findById(genre.getId())
                .orElseThrow(() -> new NotFoundException("genre with name " + genre.getName() + " does not exist"));
        genreRepository.save(genre);
        return genre;
    }

    @Override
    public Genre read(String name) {
        return genreRepository.getByName(name)
                .orElseThrow(() -> new NotFoundException("genre with name " + name + " does not exist"));
    }

    @Override
    public Genre read(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("genre with id " + id + " does not exist"));
    }


    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }
}
