package ru.otus.repository;


import org.springframework.data.repository.CrudRepository;
import ru.otus.domain.Author;

import java.util.Optional;

public interface AuthorRepository extends CrudRepository<Author, Long> {
    Optional<Author> getByName(String name);



}
