package ru.otus.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Commentary;
import java.util.List;


@Repository
public interface CommentaryRepository extends MongoRepository<Commentary, String> {

    List<Commentary> findByBookId(String bookId);



}
