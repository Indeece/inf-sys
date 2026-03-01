package ru.indeece.infsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.indeece.infsys.entities.Department;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByCode(String code);
    Optional<Department> findByEmail(String email);
    boolean existsByCode(String code);
    boolean existsByEmail(String email);

    @Query("SELECT COUNT(p) FROM Professor p WHERE p.department.id = :deptId")
    int countProfessorsByDepartmentId(@Param("deptId") Long departmentId);
}