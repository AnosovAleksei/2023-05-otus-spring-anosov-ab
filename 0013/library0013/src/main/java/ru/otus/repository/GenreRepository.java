package ru.otus.repository;


import org.springframework.data.repository.CrudRepository;
import ru.otus.domain.Genre;

import java.util.Optional;

public interface GenreRepository extends CrudRepository<Genre, Long> {

    Optional<Genre> getByName(String name);



}
