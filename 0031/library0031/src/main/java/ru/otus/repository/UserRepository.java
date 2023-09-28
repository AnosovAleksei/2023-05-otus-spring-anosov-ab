package ru.otus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.domain.User;

@RepositoryRestResource(path = "rest-user")
public interface UserRepository  extends JpaRepository<User, Long> {
}
