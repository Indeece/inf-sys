package ru.indeece.infsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.indeece.infsys.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
