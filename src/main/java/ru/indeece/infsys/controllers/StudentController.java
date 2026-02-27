package ru.indeece.infsys.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.indeece.infsys.model.Student;
import ru.indeece.infsys.service.StudentService;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Student service", description = "CRUD application for managing student records")
@Slf4j
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/add")
    @Operation(summary = "Add student", description = "Adds a new student")
    private ResponseEntity<Student> addStudent(@Parameter(description = "student entity",
            example = "Student student") @RequestBody Student student) {
        log.info("POST /api/v1/add | email: {}", student.getEmail());
        return ResponseEntity.ok(studentService.saveStudent(student));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get student", description = "Returns a student by id")
    private ResponseEntity<Student> getStudent(@Parameter(description = "student's id",
            example = "1") @PathVariable long id) {
        log.info("GET /api/v1/{id} | id: {}", id);
        return ResponseEntity.ok(studentService.getStudent(id));
    }

    @PutMapping("/update")
    @Operation(summary = "Update student", description = "Updates students's info")
    private ResponseEntity<Student> updateStudent(@Parameter(description = "student entity",
            example = "Student student") @RequestBody Student student) {
        log.info("PUT /api/v1/update | email: {}", student.getEmail());
        return ResponseEntity.ok(studentService.updateStudent(student));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete student", description = "Deletes student from DB")
    private ResponseEntity<Void> deleteStudent(@Parameter(description = "student's id",
            example = "1") @RequestParam long id) {
        studentService.deleteStudent(id);
        log.info("DELETE /api/v1/delete | id: {}", id);
        return ResponseEntity.ok().build();
    }
}
