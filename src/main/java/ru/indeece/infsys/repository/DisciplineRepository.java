package ru.indeece.infsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.indeece.infsys.entities.Discipline;
import ru.indeece.infsys.enums.ExamType;

import java.util.List;
import java.util.Optional;

@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, Long> {

    @Query("""
        SELECT d FROM Discipline d
        LEFT JOIN FETCH d.department
        WHERE d.id = :id
    """)
    Optional<Discipline> findByIdWithDepartment(@Param("id") Long id);

    @Query("""
        SELECT d FROM Discipline d
        LEFT JOIN FETCH d.department
        WHERE d.name = :name
    """)
    Optional<Discipline> findByName(@Param("name") String name);

    @Query("""
        SELECT d FROM Discipline d
        LEFT JOIN FETCH d.department
        WHERE d.department.id = :departmentId
    """)
    List<Discipline> findByDepartmentId(@Param("departmentId") Long departmentId);

    List<Discipline> findByExamType(ExamType examType);
    List<Discipline> findByHoursLessThanEqual(Integer hours);
    boolean existsByName(String name);

    @Query("SELECT COUNT(p) FROM Professor p JOIN p.disciplines d WHERE d.id = :disciplineId")
    int countProfessorsByDisciplineId(@Param("disciplineId") Long disciplineId);
}