package ru.indeece.infsys.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.indeece.infsys.dto.DepartmentDto;
import ru.indeece.infsys.entities.Department;
import ru.indeece.infsys.mappers.DepartmentMapper;
import ru.indeece.infsys.repository.DepartmentRepository;

@Service
@Slf4j
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    @Cacheable(value = "departments", key = "#id", unless = "#result == null")
    public DepartmentDto getDepartment(Long id) {
        log.info("Get department by id: {}", id);
        Department department = departmentRepository.findById(id).orElseThrow(() -> {
            log.warn("Department with id={} was not found", id);
            return new EntityNotFoundException("Department not found - " + id);
        });
        return departmentMapper.toDto(department);
    }

    @Cacheable(value = "departments", key = "'code_' + #code", unless = "#result == null")
    public DepartmentDto getDepartmentByCode(String code) {
        log.info("Get department by code: {}", code);
        Department department = departmentRepository.findByCode(code).orElseThrow(() -> {
            log.warn("Department with code={} was not found", code);
            return new EntityNotFoundException("Department not found - " + code);
        });
        return departmentMapper.toDto(department);
    }

    @CacheEvict(value = "departments", allEntries = true)
    public DepartmentDto saveDepartment(Department department) {
        log.info("Saving department with name: {}", department.getName());
        Department savedDepartment = departmentRepository.save(department);
        log.info("Saved department with id: {}", savedDepartment.getId());
        return departmentMapper.toDto(savedDepartment);
    }

    @CacheEvict(value = "departments", allEntries = true)
    public DepartmentDto updateDepartment(Department department) {
        log.info("Updating department with id: {}", department.getId());
        if (!departmentRepository.existsById(department.getId())) {
            throw new EntityNotFoundException("Department not found - " + department.getId());
        }
        Department updatedDepartment = departmentRepository.save(department);
        log.info("Updated department with id: {}", updatedDepartment.getId());
        return departmentMapper.toDto(updatedDepartment);
    }

    @CacheEvict(value = "departments", allEntries = true)
    public void deleteDepartment(Long id) {
        log.info("Deleting department with id: {}", id);
        if (!departmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Department not found - " + id);
        }
        departmentRepository.deleteById(id);
        log.info("Deleted department with id: {}", id);
    }
}