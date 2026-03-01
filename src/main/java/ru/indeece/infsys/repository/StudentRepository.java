package ru.indeece.infsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.indeece.infsys.entities.Student;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("""
        SELECT s FROM Student s
        LEFT JOIN FETCH s.group
        WHERE s.id = :id
    """)
    Optional<Student> findByIdWithGroup(@Param("id") Long id);

    @Query("""
        SELECT s FROM Student s
        LEFT JOIN FETCH s.group
        WHERE s.email = :email
    """)
    Optional<Student> findByEmail(@Param("email") String email);

    @Query("""
        SELECT s FROM Student s
        LEFT JOIN FETCH s.group
        WHERE s.group.id = :groupId
        ORDER BY s.lastName
    """)
    List<Student> findByGroupId(@Param("groupId") Long groupId);

    @Query("""
        SELECT s FROM Student s
        LEFT JOIN FETCH s.group
        WHERE s.dateOfBirth BETWEEN :startDate AND :endDate
    """)
    List<Student> findByDateOfBirthBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("""
        SELECT s FROM Student s
        LEFT JOIN FETCH s.group
        WHERE s.lastName LIKE %:lastName%
    """)
    List<Student> findByLastNameContaining(@Param("lastName") String lastName);

    @Query("""
        SELECT s FROM Student s
        LEFT JOIN FETCH s.group
        WHERE s.group.id = :groupId
        ORDER BY s.lastName
    """)
    List<Student> findByGroupIdOrdered(@Param("groupId") Long groupId);

    @Query("SELECT COUNT(s) FROM Student s WHERE s.group.id = :groupId")
    long countByGroupId(@Param("groupId") Long groupId);

    boolean existsByEmail(String email);
}