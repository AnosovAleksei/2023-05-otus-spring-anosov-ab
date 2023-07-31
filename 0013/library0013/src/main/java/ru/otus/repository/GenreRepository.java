package ru.otus.repository;


import org.springframework.data.repository.CrudRepository;
import ru.otus.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends CrudRepository<Genre, Long> {

    @Override
    List<Genre> findAll();

    Optional<Genre> getByName(String name);



}
