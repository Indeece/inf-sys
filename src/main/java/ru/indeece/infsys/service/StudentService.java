package ru.indeece.infsys.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.indeece.infsys.model.Student;
import ru.indeece.infsys.repository.StudentRepository;

@Service
@Slf4j
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Cacheable(value = "students", key = "#id", unless = "#result == null")
    public Student getStudent(Long id) {
        log.info("Get student by id: {}", id);
        return studentRepository.findById(id).orElseThrow(() -> {
            log.warn("Student with id={} was not found", id);
            return new EntityNotFoundException("Student not found - " + id);
        });
    }

    @CacheEvict(value = "students", key = "#student.id")
    public Student saveStudent(Student student) {
        log.info("Saving student with email: {}", student.getEmail());
        Student savedStudent = studentRepository.save(student);
        log.info("Saved student with id: {}", savedStudent.getId());
        return savedStudent;
    }

    @CachePut(value = "students", key = "#student.id")
    public Student updateStudent(Student student) {
        log.info("Updating student with id: {}", student.getId());
        Student updatedStudent = studentRepository.save(student);
        log.info("Updated student with id: {}", updatedStudent.getId());
        return updatedStudent;
    }

    @CacheEvict(value = "students", key = "#id")
    public void deleteStudent(Long id) {
        log.info("Deleting student with id: {}", id);
        Student student = studentRepository.findById(id).orElseThrow(() -> {
            log.warn("Student with id={} was not found", id);
            return new EntityNotFoundException("Student not found - " + id);
        });
        studentRepository.delete(student);
        log.info("Deleted student with id: {}", id);
    }
}
