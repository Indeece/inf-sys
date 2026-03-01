package ru.indeece.infsys.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.indeece.infsys.dto.DisciplineDto;
import ru.indeece.infsys.entities.Discipline;
import ru.indeece.infsys.service.DisciplineService;

@RestController
@RequestMapping("/api/v1/discipline")
@Tag(name = "Discipline service", description = "CRUD operations for disciplines")
@Slf4j
public class DisciplineController {

    private final DisciplineService disciplineService;

    public DisciplineController(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @PostMapping("/add")
    @Operation(summary = "Add discipline", description = "Adds a new discipline")
    public ResponseEntity<DisciplineDto> addDiscipline(@RequestBody Discipline discipline) {
        log.info("POST /api/v1/discipline/add | name: {}", discipline.getName());
        return ResponseEntity.ok(disciplineService.saveDiscipline(discipline));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get discipline", description = "Returns a discipline by id")
    public ResponseEntity<DisciplineDto> getDiscipline(@PathVariable Long id) {
        log.info("GET /api/v1/discipline/{}", id);
        return ResponseEntity.ok(disciplineService.getDiscipline(id));
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Get discipline by name", description = "Returns a discipline by its name")
    public ResponseEntity<DisciplineDto> getDisciplineByName(@PathVariable String name) {
        log.info("GET /api/v1/discipline/name/{}", name);
        return ResponseEntity.ok(disciplineService.getDisciplineByName(name));
    }

    @PutMapping("/update")
    @Operation(summary = "Update discipline", description = "Updates discipline's info")
    public ResponseEntity<DisciplineDto> updateDiscipline(@RequestBody Discipline discipline) {
        log.info("PUT /api/v1/discipline/update | id: {}", discipline.getId());
        return ResponseEntity.ok(disciplineService.updateDiscipline(discipline));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete discipline", description = "Deletes discipline from DB")
    public ResponseEntity<Void> deleteDiscipline(@RequestParam Long id) {
        log.info("DELETE /api/v1/discipline/delete | id: {}", id);
        disciplineService.deleteDiscipline(id);
        return ResponseEntity.ok().build();
    }
}