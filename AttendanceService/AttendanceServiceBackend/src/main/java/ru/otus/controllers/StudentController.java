package ru.otus.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.dto.StudentRequestDto;
import ru.otus.dto.StudentResponceDto;
import ru.otus.service.StudentAttendanceService;
import ru.otus.service.StudentService;
import ru.otus.service.UserAccessService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StudentController {

    private final StudentAttendanceService studentAttendanceService;

    private final UserAccessService userAccessService;

    private final StudentService studentService;


    @GetMapping("/api/v1/{teamId}/student")
    public List<StudentResponceDto> getStudents(@PathVariable Long teamId) {
        return studentService.getStudentByTeamId(teamId);
    }

    @GetMapping("/api/v1/{teamId}/student/{controlDate}")
    public List<StudentResponceDto> getStudentsByDate(@PathVariable Long teamId, @PathVariable String controlDate) {
        return studentAttendanceService.getStudentByTeamIdForDate(teamId, controlDate);
    }

    @PostMapping("/api/v1/{teamId}/student")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void setStudents(@PathVariable Long teamId, @RequestBody List<StudentRequestDto> studentRequestDtoList) {
        //TODO add check student for command
        studentAttendanceService.setStudentAttendanceControl(studentRequestDtoList);
    }

    @GetMapping("/api/v1/{teamId}/control-date")
    public List<String> getDateList(@PathVariable Long teamId) {
        return studentAttendanceService.getControlDateList(teamId);
    }

    @GetMapping("/api/v1/{teamId}/control-write-date")
    public String getLastDateList(@PathVariable Long teamId) {
        List<String> stringList = studentAttendanceService.getControlDateList(teamId);
        if(stringList!=null && stringList.size()>0){
            return stringList.get(stringList.size()-1);
        }
        return null;
    }
}
