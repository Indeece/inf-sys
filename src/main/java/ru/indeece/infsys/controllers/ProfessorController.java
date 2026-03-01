package ru.indeece.infsys.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.indeece.infsys.dto.ProfessorDto;
import ru.indeece.infsys.entities.Professor;
import ru.indeece.infsys.service.ProfessorService;

@RestController
@RequestMapping("/api/v1/professor")
@Tag(name = "Professor service", description = "CRUD operations for professors")
@Slf4j
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @PostMapping("/add")
    @Operation(summary = "Add professor", description = "Adds a new professor")
    public ResponseEntity<ProfessorDto> addProfessor(@RequestBody Professor professor) {
        log.info("POST /api/v1/professor/add | email: {}", professor.getEmail());
        return ResponseEntity.ok(professorService.saveProfessor(professor));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get professor", description = "Returns a professor by id")
    public ResponseEntity<ProfessorDto> getProfessor(@PathVariable Long id) {
        log.info("GET /api/v1/professor/{}", id);
        return ResponseEntity.ok(professorService.getProfessor(id));
    }

    @PutMapping("/update")
    @Operation(summary = "Update professor", description = "Updates professor's info")
    public ResponseEntity<ProfessorDto> updateProfessor(@RequestBody Professor professor) {
        log.info("PUT /api/v1/professor/update | id: {}", professor.getId());
        return ResponseEntity.ok(professorService.updateProfessor(professor));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete professor", description = "Deletes professor from DB")
    public ResponseEntity<Void> deleteProfessor(@RequestParam Long id) {
        log.info("DELETE /api/v1/professor/delete | id: {}", id);
        professorService.deleteProfessor(id);
        return ResponseEntity.ok().build();
    }
}