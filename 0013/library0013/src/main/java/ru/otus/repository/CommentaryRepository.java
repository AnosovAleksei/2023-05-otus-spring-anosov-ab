package ru.otus.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.Commentary;
import java.util.List;


public interface CommentaryRepository extends JpaRepository<Commentary, Long> {


    List<Commentary> findByBookId(Long bookId);



}
