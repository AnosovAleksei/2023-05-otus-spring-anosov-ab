package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Genre;
import ru.otus.dto.GenreCreateDto;
import ru.otus.dto.GenreUpdateDto;
import ru.otus.repository.GenreRepository;

import java.util.List;


@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Transactional
    public Genre create(Genre genre) {
        try {
            genreRepository.save(genre);
        } catch (DataIntegrityViolationException e) {
            throw new DataAlreadyExistsException("Жанр с таким названием уже есть в системе", e);
        }
        return genre;
    }

    @Override
    public Genre create(GenreCreateDto genreCreateDto) {
        return create(new Genre(genreCreateDto.getName()));
    }

    @Override
    public Genre update(GenreUpdateDto genreUpdateDto) {
        return update(new Genre(genreUpdateDto.getId(), genreUpdateDto.getName()));
    }

    @Transactional
    public Genre update(Genre genre) {
        genreRepository.findById(genre.getId())
                .orElseThrow(() -> new NotFoundException("genre with name " + genre.getName() + " does not exist"));
        genreRepository.save(genre);
        return genre;
    }

    @Override
    @Transactional(readOnly = true)
    public Genre read(String name) {
        return genreRepository.getByName(name)
                .orElseThrow(() -> new NotFoundException("genre with name " + name + " does not exist"));
    }

    @Override
    @Transactional(readOnly = true)
    public Genre read(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("genre with id " + id + " does not exist"));
    }


    @Override
    @Transactional(readOnly = true)
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }
}
