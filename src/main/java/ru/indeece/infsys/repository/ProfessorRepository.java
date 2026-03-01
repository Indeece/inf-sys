package ru.indeece.infsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.indeece.infsys.entities.Professor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    @Query("""
        SELECT p FROM Professor p
        LEFT JOIN FETCH p.department
        LEFT JOIN FETCH p.disciplines
        WHERE p.id = :id
    """)
    Optional<Professor> findByIdWithRelations(@Param("id") Long id);

    @Query("""
        SELECT p FROM Professor p
        LEFT JOIN FETCH p.department
        LEFT JOIN FETCH p.disciplines
        WHERE p.email = :email
    """)
    Optional<Professor> findByEmail(@Param("email") String email);

    @Query("""
        SELECT p FROM Professor p
        LEFT JOIN FETCH p.department
        LEFT JOIN FETCH p.disciplines
        WHERE p.department.id = :departmentId
    """)
    List<Professor> findByDepartmentId(@Param("departmentId") Long departmentId);

    @Query("""
        SELECT p FROM Professor p
        LEFT JOIN FETCH p.department
        LEFT JOIN FETCH p.disciplines
        WHERE p.hireDate > :date
    """)
    List<Professor> findByHireDateAfter(@Param("date") LocalDate date);

    @Query("""
        SELECT p FROM Professor p
        LEFT JOIN FETCH p.department
        LEFT JOIN FETCH p.disciplines
        WHERE p.hireDate < :date
    """)
    List<Professor> findByHireDateBefore(@Param("date") LocalDate date);

    @Query("""
        SELECT p FROM Professor p
        LEFT JOIN FETCH p.department
        LEFT JOIN FETCH p.disciplines
        WHERE p.lastName LIKE %:lastName%
    """)
    List<Professor> findByLastNameContaining(@Param("lastName") String lastName);

    @Query("""
        SELECT p FROM Professor p
        LEFT JOIN FETCH p.department
        LEFT JOIN FETCH p.disciplines d
        WHERE d.id = :disciplineId
    """)
    List<Professor> findByDisciplineId(@Param("disciplineId") Long disciplineId);

    boolean existsByEmail(String email);
}