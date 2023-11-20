package ru.otus.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.UserAccess;



public interface UserAccessRepository extends JpaRepository<UserAccess, Long> {

    @EntityGraph(attributePaths = {"team"})
    UserAccess findAllByUsername(String username);
}
