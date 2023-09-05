package ru.otus.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.otus.domain.Commentary;



@Repository
public interface CommentaryRepository extends ReactiveMongoRepository<Commentary, String> {

    Mono<Commentary> save(Mono<Commentary> commentary);

}
