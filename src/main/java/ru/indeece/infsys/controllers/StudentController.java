package ru.indeece.infsys.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.indeece.infsys.dto.StudentDto;
import ru.indeece.infsys.entities.Student;
import ru.indeece.infsys.service.StudentService;

@RestController
@RequestMapping("/api/v1/student")
@Tag(name = "Student service", description = "CRUD application for managing student records")
@Slf4j
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/add")
    @Operation(summary = "Add student", description = "Adds a new student")
    public ResponseEntity<StudentDto> addStudent(@RequestBody Student student) {
        log.info("POST /api/v1/student/add | email: {}", student.getEmail());
        return ResponseEntity.ok(studentService.saveStudent(student));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get student", description = "Returns a student by id")
    public ResponseEntity<StudentDto> getStudent(@PathVariable long id) {
        log.info("GET /api/v1/student/{}", id);
        return ResponseEntity.ok(studentService.getStudent(id));
    }

    @PutMapping("/update")
    @Operation(summary = "Update student", description = "Updates student's info")
    public ResponseEntity<StudentDto> updateStudent(@RequestBody Student student) {
        log.info("PUT /api/v1/student/update | id: {}", student.getId());
        return ResponseEntity.ok(studentService.updateStudent(student));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete student", description = "Deletes student from DB")
    public ResponseEntity<Void> deleteStudent(@RequestParam long id) {
        log.info("DELETE /api/v1/student/delete | id: {}", id);
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}