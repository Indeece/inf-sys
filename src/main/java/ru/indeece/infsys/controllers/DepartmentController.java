package ru.indeece.infsys.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.indeece.infsys.dto.DepartmentDto;
import ru.indeece.infsys.entities.Department;
import ru.indeece.infsys.service.DepartmentService;

@RestController
@RequestMapping("/api/v1/department")
@Tag(name = "Department service", description = "CRUD operations for departments")
@Slf4j
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/add")
    @Operation(summary = "Add department", description = "Adds a new department")
    public ResponseEntity<DepartmentDto> addDepartment(@RequestBody Department department) {
        log.info("POST /api/v1/department/add | name: {}, code: {}",
                department.getName(), department.getCode());
        return ResponseEntity.ok(departmentService.saveDepartment(department));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get department", description = "Returns a department by id")
    public ResponseEntity<DepartmentDto> getDepartment(@PathVariable Long id) {
        log.info("GET /api/v1/department/{}", id);
        return ResponseEntity.ok(departmentService.getDepartment(id));
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Get department by code", description = "Returns a department by its code")
    public ResponseEntity<DepartmentDto> getDepartmentByCode(@PathVariable String code) {
        log.info("GET /api/v1/department/code/{}", code);
        return ResponseEntity.ok(departmentService.getDepartmentByCode(code));
    }

    @PutMapping("/update")
    @Operation(summary = "Update department", description = "Updates department's info")
    public ResponseEntity<DepartmentDto> updateDepartment(@RequestBody Department department) {
        log.info("PUT /api/v1/department/update | id: {}", department.getId());
        return ResponseEntity.ok(departmentService.updateDepartment(department));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete department", description = "Deletes department from DB")
    public ResponseEntity<Void> deleteDepartment(@RequestParam Long id) {
        log.info("DELETE /api/v1/department/delete | id: {}", id);
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok().build();
    }
}