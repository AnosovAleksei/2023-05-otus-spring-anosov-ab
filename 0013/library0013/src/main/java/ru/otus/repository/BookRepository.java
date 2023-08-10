package ru.otus.repository;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Book;

import java.util.List;
import java.util.Optional;

@Transactional
public interface BookRepository extends CrudRepository<Book, Long> {

    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findAll();

    Optional<Book> getByName(String name);

    Optional<Book> getById(Long id);

    List<Book> getByAuthorId(Long id);

    List<Book> getByGenreId(Long id);


}
