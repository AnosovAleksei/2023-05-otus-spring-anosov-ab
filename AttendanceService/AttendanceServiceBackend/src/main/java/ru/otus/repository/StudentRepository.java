package ru.otus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.Student;

import java.util.List;


public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByTeamId(Long teamId);

}
