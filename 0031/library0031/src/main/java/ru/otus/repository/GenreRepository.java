package ru.otus.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.domain.Genre;


import java.util.Optional;

@RepositoryRestResource(path = "rest-genre")
public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> getByName(String name);


}
