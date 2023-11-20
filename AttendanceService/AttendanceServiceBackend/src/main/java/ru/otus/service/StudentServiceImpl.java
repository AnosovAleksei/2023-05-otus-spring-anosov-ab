package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.NotFoundException;
import ru.otus.domain.Student;
import ru.otus.dto.StudentResponceDto;
import ru.otus.repository.StudentRepository;

import java.util.List;


@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public List<StudentResponceDto> getStudentByTeamId(Long teamId) {
        List<Student> students = studentRepository.findByTeamId(teamId);

        if(students == null){
            throw new NotFoundException("student with team id :" + teamId + " does not exist");
        }

        return students
                .stream().map(m -> new StudentResponceDto(m.getId(),
                        m.getSurname(),
                        m.getName(),
                        m.getPatronymic(),
                        true)).toList();

    }

}
