package ru.otus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.domain.StudentAttendance;

import java.util.List;


@Repository
public interface StudentAttendanceRepository extends JpaRepository<StudentAttendance, Long> {
    @Query(
            value = """
                    SELECT distinct rre.control_date as dt
                    FROM student_attendance rre
                    left join student s on s.student_id = rre.student_id
                    where s.team_id = :teamId
                    order by dt""",
            nativeQuery = true)
    List<String> findDateByTeamId(@Param("teamId") Long teamId);


    List<StudentAttendance> findByStudentTeamIdAndControlDateOrderByStudentSurname(Long teamId, String controlDate);
}
