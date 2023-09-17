package ru.otus.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.Genre;


import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> getByName(String name);


}
