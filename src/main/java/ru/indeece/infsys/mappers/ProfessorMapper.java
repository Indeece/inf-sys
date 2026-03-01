package ru.indeece.infsys.mappers;

import org.springframework.stereotype.Component;
import ru.indeece.infsys.dto.ProfessorDto;
import ru.indeece.infsys.entities.Professor;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class ProfessorMapper {

    public ProfessorDto toDto(Professor professor) {
        if (professor == null) return null;

        ProfessorDto.ProfessorDtoBuilder builder = ProfessorDto.builder()
                .id(professor.getId())
                .firstName(professor.getFirstName())
                .lastName(professor.getLastName())
                .hireDate(professor.getHireDate())
                .email(professor.getEmail());

        if (professor.getDepartment() != null) {
            builder.departmentId(professor.getDepartment().getId())
                    .departmentName(professor.getDepartment().getName())
                    .departmentCode(professor.getDepartment().getCode());
        }

        if (professor.getDisciplines() != null) {
            builder.disciplineIds(
                    professor.getDisciplines().stream()
                            .map(d -> d.getId())
                            .collect(Collectors.toList())
            );
            builder.disciplineNames(
                    professor.getDisciplines().stream()
                            .map(d -> d.getName())
                            .collect(Collectors.toList())
            );
        } else {
            builder.disciplineIds(Collections.emptyList());
            builder.disciplineNames(Collections.emptyList());
        }

        return builder.build();
    }
}