package ru.otus.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.domain.Commentary;
import java.util.List;


@RepositoryRestResource(path = "rest-commentary")
public interface CommentaryRepository extends JpaRepository<Commentary, Long> {


    List<Commentary> findByBookId(Long bookId);



}
