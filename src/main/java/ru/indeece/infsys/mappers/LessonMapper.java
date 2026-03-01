package ru.indeece.infsys.mappers;

import org.springframework.stereotype.Component;
import ru.indeece.infsys.dto.LessonDto;
import ru.indeece.infsys.entities.Lesson;

@Component
public class LessonMapper {

    public LessonDto toDto(Lesson lesson) {
        if (lesson == null) return null;

        LessonDto.LessonDtoBuilder builder = LessonDto.builder()
                .id(lesson.getId())
                .date(lesson.getDate())
                .startTime(lesson.getStartTime())
                .endTime(lesson.getEndTime())
                .lessonType(lesson.getLessonType())
                .classroom(lesson.getClassroom());

        if (lesson.getGroup() != null) {
            builder.groupId(lesson.getGroup().getId())
                    .groupCode(lesson.getGroup().getCode());
        }

        if (lesson.getDiscipline() != null) {
            builder.disciplineId(lesson.getDiscipline().getId())
                    .disciplineName(lesson.getDiscipline().getName());
        }

        if (lesson.getProfessor() != null) {
            builder.professorId(lesson.getProfessor().getId())
                    .professorFirstName(lesson.getProfessor().getFirstName())
                    .professorLastName(lesson.getProfessor().getLastName());
        }

        return builder.build();
    }
}