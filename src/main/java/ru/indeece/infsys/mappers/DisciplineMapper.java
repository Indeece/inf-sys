package ru.indeece.infsys.mappers;

import org.springframework.stereotype.Component;
import ru.indeece.infsys.dto.DisciplineDto;
import ru.indeece.infsys.entities.Discipline;
import ru.indeece.infsys.repository.DisciplineRepository;

@Component
public class DisciplineMapper {

    private final DisciplineRepository disciplineRepository;

    public DisciplineMapper(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    public DisciplineDto toDto(Discipline discipline) {
        if (discipline == null) return null;

        DisciplineDto.DisciplineDtoBuilder builder = DisciplineDto.builder()
                .id(discipline.getId())
                .name(discipline.getName())
                .hours(discipline.getHours())
                .examType(discipline.getExamType())
                .professorsCount(disciplineRepository.countProfessorsByDisciplineId(discipline.getId()));

        if (discipline.getDepartment() != null) {
            builder.departmentId(discipline.getDepartment().getId())
                    .departmentName(discipline.getDepartment().getName());
        }

        return builder.build();
    }
}