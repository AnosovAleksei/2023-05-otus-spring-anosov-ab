package ru.otus.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Genre;

import java.util.Optional;

@Repository
public interface GenreRepository extends MongoRepository<Genre, String> {

    Optional<Genre> getByName(String name);



}
