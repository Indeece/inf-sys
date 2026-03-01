package ru.indeece.infsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.indeece.infsys.entities.Lesson;
import ru.indeece.infsys.enums.LessonType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    @Query("""
        SELECT l FROM Lesson l
        JOIN FETCH l.group
        JOIN FETCH l.discipline
        JOIN FETCH l.professor p
        LEFT JOIN FETCH p.department
        WHERE l.group.id = :groupId AND l.date = :date
        ORDER BY l.startTime
    """)
    List<Lesson> findByGroupIdAndDate(
            @Param("groupId") Long groupId,
            @Param("date") LocalDate date
    );

    @Query("""
        SELECT l FROM Lesson l
        JOIN FETCH l.group
        JOIN FETCH l.discipline
        JOIN FETCH l.professor p
        LEFT JOIN FETCH p.department
        WHERE l.professor.id = :professorId AND l.date = :date
        ORDER BY l.startTime
    """)
    List<Lesson> findByProfessorIdAndDate(
            @Param("professorId") Long professorId,
            @Param("date") LocalDate date
    );

    @Query("""
        SELECT l FROM Lesson l
        JOIN FETCH l.group
        JOIN FETCH l.discipline
        JOIN FETCH l.professor p
        LEFT JOIN FETCH p.department
        WHERE l.discipline.id = :disciplineId AND l.date = :date
        ORDER BY l.startTime
    """)
    List<Lesson> findByDisciplineIdAndDate(
            @Param("disciplineId") Long disciplineId,
            @Param("date") LocalDate date
    );

    @Query("""
        SELECT l FROM Lesson l
        JOIN FETCH l.group
        JOIN FETCH l.discipline
        JOIN FETCH l.professor p
        LEFT JOIN FETCH p.department
        WHERE l.group.id = :groupId
        AND l.date BETWEEN :startDate AND :endDate
        ORDER BY l.date, l.startTime
    """)
    List<Lesson> findByGroupIdAndDateBetween(
            @Param("groupId") Long groupId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("""
        SELECT l FROM Lesson l
        JOIN FETCH l.group
        JOIN FETCH l.discipline
        JOIN FETCH l.professor p
        LEFT JOIN FETCH p.department
        WHERE l.professor.id = :professorId
        AND l.date BETWEEN :startDate AND :endDate
        ORDER BY l.date, l.startTime
    """)
    List<Lesson> findByProfessorIdAndDateBetween(
            @Param("professorId") Long professorId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("""
        SELECT l FROM Lesson l
        JOIN FETCH l.group
        JOIN FETCH l.discipline
        JOIN FETCH l.professor p
        LEFT JOIN FETCH p.department
        WHERE l.group.id = :groupId AND l.lessonType = :lessonType
        ORDER BY l.date, l.startTime
    """)
    List<Lesson> findByGroupIdAndLessonType(
            @Param("groupId") Long groupId,
            @Param("lessonType") LessonType lessonType
    );

    @Query("""
        SELECT l FROM Lesson l
        JOIN FETCH l.group
        JOIN FETCH l.discipline
        JOIN FETCH l.professor p
        LEFT JOIN FETCH p.department
        WHERE l.classroom = :classroom AND l.date = :date
        ORDER BY l.startTime
    """)
    List<Lesson> findByClassroomAndDate(
            @Param("classroom") String classroom,
            @Param("date") LocalDate date
    );

    @Query("""
        SELECT l FROM Lesson l
        JOIN FETCH l.group
        JOIN FETCH l.discipline
        JOIN FETCH l.professor p
        LEFT JOIN FETCH p.department
        WHERE l.professor.id = :professorId
        AND l.date BETWEEN :startDate AND :endDate
        ORDER BY l.date, l.startTime
    """)
    List<Lesson> findProfessorSchedule(
            @Param("professorId") Long professorId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("""
        SELECT l FROM Lesson l
        JOIN FETCH l.group
        JOIN FETCH l.discipline
        JOIN FETCH l.professor p
        LEFT JOIN FETCH p.department
        WHERE l.id = :id
    """)
    Optional<Lesson> findByIdWithRelations(@Param("id") Long id);

    boolean existsByGroupIdAndDateAndStartTime(Long groupId, LocalDate date, LocalTime startTime);
}