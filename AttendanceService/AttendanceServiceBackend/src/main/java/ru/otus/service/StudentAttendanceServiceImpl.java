package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.NotFoundException;
import ru.otus.domain.Student;
import ru.otus.domain.StudentAttendance;
import ru.otus.dto.StudentRequestDto;
import ru.otus.dto.StudentResponceDto;
import ru.otus.repository.StudentAttendanceRepository;
import ru.otus.repository.StudentRepository;
import ru.otus.util.Converter;

import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class StudentAttendanceServiceImpl implements StudentAttendanceService{

    private final StudentAttendanceRepository studentAttendanceRepository;

    private final StudentRepository studentRepository;

    @Override
    public List<String> getControlDateList(Long teamId) {
        return studentAttendanceRepository.findDateByTeamId(teamId);
    }

    @Override
    public List<StudentResponceDto> getStudentByTeamIdForDate(Long teamId, String dataControl) {

        return studentAttendanceRepository.
                findByStudentTeamIdAndControlDateOrderByStudentSurname(teamId, dataControl).
                stream().map(m->new StudentResponceDto(m.getStudent().getId(),
                        m.getStudent().getSurname(),
                        m.getStudent().getName(),
                        m.getStudent().getPatronymic(),
                        m.getStatus())).toList();
    }

    @Override
    public void setStudentAttendanceControl(List<StudentRequestDto> studentRequestDtoList) {

        String controlDate = Converter.dateToString(new Date());

        List<StudentAttendance> studentAttendances = studentRequestDtoList.stream().map(m-> {
            Student student = studentRepository.findById(m.getId())
                    .orElseThrow(() -> new NotFoundException("student with id" + m.getId() + "does not exist"));
            return new StudentAttendance(student, controlDate, m.getStatus());
        }).toList();

        studentAttendanceRepository.saveAll(studentAttendances);
    }
}
