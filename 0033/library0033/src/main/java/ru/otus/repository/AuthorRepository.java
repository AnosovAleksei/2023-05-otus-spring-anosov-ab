package ru.otus.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.domain.Author;

import java.util.Optional;


@RepositoryRestResource(path = "rest-author")
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @RestResource(path = "rest-author", rel = "rest-author")
    Optional<Author> getByName(String name);



}
