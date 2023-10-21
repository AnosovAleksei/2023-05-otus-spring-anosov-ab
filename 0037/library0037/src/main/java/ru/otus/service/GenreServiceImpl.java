package ru.otus.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

    @HystrixCommand(commandKey = "getRentsKey")
    @Transactional
    public Genre create(Genre genre) {
        try {
            genreRepository.save(genre);
        } catch (DataIntegrityViolationException e) {
            throw new DataAlreadyExistsException("Жанр с таким названием уже есть в системе", e);
        }
        return genre;
    }

    @HystrixCommand(commandKey = "getRentsKey")
    @Override
    public Genre create(GenreCreateDto genreCreateDto) {
        return create(new Genre(genreCreateDto.getName()));
    }

    @HystrixCommand(commandKey = "getRentsKey")
    @Override
    public Genre update(GenreUpdateDto genreUpdateDto) {
        return update(new Genre(genreUpdateDto.getId(), genreUpdateDto.getName()));
    }

    @HystrixCommand(commandKey = "getRentsKey")
    @Transactional
    public Genre update(Genre genre) {
        genreRepository.findById(genre.getId())
                .orElseThrow(() -> new NotFoundException("genre with name " + genre.getName() + " does not exist"));
        genreRepository.save(genre);
        return genre;
    }

    @HystrixCommand(commandKey = "getRentsKey")
    @Override
    @Transactional(readOnly = true)
    public Genre read(String name) {
        return genreRepository.getByName(name)
                .orElseThrow(() -> new NotFoundException("genre with name " + name + " does not exist"));
    }

    @HystrixCommand(commandKey = "getRentsKey")
    @Override
    @Transactional(readOnly = true)
    public Genre read(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("genre with id " + id + " does not exist"));
    }


    @HystrixCommand(commandKey = "getRentsKey")
    @Override
    @Transactional(readOnly = true)
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }
}
