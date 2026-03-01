package ru.indeece.infsys.mappers;

import org.springframework.stereotype.Component;
import ru.indeece.infsys.dto.DepartmentDto;
import ru.indeece.infsys.entities.Department;
import ru.indeece.infsys.repository.DepartmentRepository;

@Component
public class DepartmentMapper {

    private final DepartmentRepository departmentRepository;

    public DepartmentMapper(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public DepartmentDto toDto(Department department) {
        if (department == null) return null;

        return DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .code(department.getCode())
                .email(department.getEmail())
                .professorsCount(departmentRepository.countProfessorsByDepartmentId(department.getId()))
                .build();
    }
}