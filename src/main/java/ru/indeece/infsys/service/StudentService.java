package ru.indeece.infsys.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.indeece.infsys.dto.StudentDto;
import ru.indeece.infsys.entities.Student;
import ru.indeece.infsys.mappers.StudentMapper;
import ru.indeece.infsys.repository.StudentRepository;

@Service
@Slf4j
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Cacheable(value = "students", key = "#id", unless = "#result == null")
    public StudentDto getStudent(Long id) {
        log.info("Get student by id: {}", id);
        Student student = studentRepository.findByIdWithGroup(id).orElseThrow(() -> {
            log.warn("Student with id={} was not found", id);
            return new EntityNotFoundException("Student not found - " + id);
        });
        return studentMapper.toDto(student);
    }

    @CacheEvict(value = "students", allEntries = true)
    public StudentDto saveStudent(Student student) {
        log.info("Saving student with email: {}", student.getEmail());
        Student savedStudent = studentRepository.save(student);
        log.info("Saved student with id: {}", savedStudent.getId());
        return studentMapper.toDto(savedStudent);
    }

    @CacheEvict(value = "students", allEntries = true)
    public StudentDto updateStudent(Student student) {
        log.info("Updating student with id: {}", student.getId());
        if (!studentRepository.existsById(student.getId())) {
            throw new EntityNotFoundException("Student not found - " + student.getId());
        }
        Student updatedStudent = studentRepository.save(student);
        log.info("Updated student with id: {}", updatedStudent.getId());
        return studentMapper.toDto(updatedStudent);
    }

    @CacheEvict(value = "students", allEntries = true)
    public void deleteStudent(Long id) {
        log.info("Deleting student with id: {}", id);
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Student not found - " + id);
        }
        studentRepository.deleteById(id);
        log.info("Deleted student with id: {}", id);
    }
}