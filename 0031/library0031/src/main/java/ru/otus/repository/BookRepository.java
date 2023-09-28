package ru.otus.repository;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Book;

import java.util.List;
import java.util.Optional;


@RepositoryRestResource(path = "rest-book")
@Transactional
public interface BookRepository extends CrudRepository<Book, Long> {

    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findAll();

    @EntityGraph(attributePaths = {"author", "genre"})
    Optional<Book> getByName(String name);

    @EntityGraph(attributePaths = {"author", "genre"})
    Optional<Book> getById(Long id);

    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> getByAuthorId(Long id);

    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> getByGenreId(Long id);


}
