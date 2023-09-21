package ru.otus.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Book;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface BookRepository extends MongoRepository<Book, String> {


    Optional<Book> getByName(String name);

    Optional<Book> getById(String id);

    List<Book> getByAuthorId(String id);

    List<Book> getByGenreId(String id);



}
