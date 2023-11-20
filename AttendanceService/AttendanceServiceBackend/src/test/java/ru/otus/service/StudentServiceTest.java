package ru.otus.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.domain.NotFoundException;
import ru.otus.domain.Student;
import ru.otus.domain.Team;
import ru.otus.domain.UserAccess;
import ru.otus.dto.StudentResponceDto;
import ru.otus.dto.TeamDto;
import ru.otus.repository.StudentRepository;
import ru.otus.repository.UserAccessRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@DisplayName("Проверка работы StudentService")
@SpringBootTest(properties = {"spring.liquibase.enabled=false"})
public class StudentServiceTest {

    @MockBean
    StudentRepository studentRepository;


    @Autowired
    StudentService studentService;


    @DisplayName("check null")
    @Test
    public void getStudentByTeamIdCheckNull(){

        given(studentRepository.findByTeamId(anyLong())).willReturn(null);

        Assertions.assertThrows(NotFoundException.class, () ->
                studentService.getStudentByTeamId(1L));
    }

    @DisplayName("check work")
    @Test
    public void getStudentByTeamId(){

        List<Student> students = List.of(
                new Student(1L, new Team(1L, "xxx"), "a", "b", "c")
        );

        given(studentRepository.findByTeamId(anyLong())).willReturn(students);

        List<StudentResponceDto> studentResponceDtoList = studentService.getStudentByTeamId(1L);
        Assertions.assertEquals(studentResponceDtoList.size(), 1);
    }

}
