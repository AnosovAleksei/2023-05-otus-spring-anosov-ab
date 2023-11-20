package ru.otus.service;

import ru.otus.dto.StudentResponceDto;

import java.util.List;

public interface StudentService {

    List<StudentResponceDto> getStudentByTeamId(Long teamId);

}
