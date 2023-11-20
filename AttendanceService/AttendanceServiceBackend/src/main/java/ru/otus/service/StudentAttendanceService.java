package ru.otus.service;

import ru.otus.dto.StudentRequestDto;
import ru.otus.dto.StudentResponceDto;

import java.util.List;

public interface StudentAttendanceService {

    List<String> getControlDateList(Long teamId);

    List<StudentResponceDto> getStudentByTeamIdForDate(Long teamId, String dataControl);

    void setStudentAttendanceControl(List<StudentRequestDto> studentRequestDtoList);

}
