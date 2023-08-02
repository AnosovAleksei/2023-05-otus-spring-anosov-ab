package ru.otus.repository;


import org.springframework.data.repository.CrudRepository;
import ru.otus.domain.Commentary;
import java.util.List;


public interface CommentaryRepository extends CrudRepository<Commentary, Long> {

    @Override
    List<Commentary> findAll();


    List<Commentary> findByBookId(Long bookId);



}
