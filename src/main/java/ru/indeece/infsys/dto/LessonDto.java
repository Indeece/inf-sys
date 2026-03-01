package ru.indeece.infsys.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.indeece.infsys.enums.LessonType;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "LessonDto", description = "Lesson data transfer object")
public class LessonDto implements Serializable {
    private static final long serialVersionUID = 103L;

    @Schema(description = "Lesson ID", example = "1")
    private Long id;

    @Schema(description = "Lesson date", example = "2024-03-15")
    private LocalDate date;

    @Schema(description = "Start time", example = "09:00")
    private LocalTime startTime;

    @Schema(description = "End time", example = "10:30")
    private LocalTime endTime;

    @Schema(description = "Lesson type", example = "LECTURE")
    private LessonType lessonType;

    @Schema(description = "Classroom", example = "А-301")
    private String classroom;

    @Schema(description = "Group ID", example = "1")
    private Long groupId;

    @Schema(description = "Group code", example = "ИС-21")
    private String groupCode;

    @Schema(description = "Discipline ID", example = "1")
    private Long disciplineId;

    @Schema(description = "Discipline name", example = "Математика")
    private String disciplineName;

    @Schema(description = "Professor ID", example = "1")
    private Long professorId;

    @Schema(description = "Professor first name", example = "Иван")
    private String professorFirstName;

    @Schema(description = "Professor last name", example = "Петров")
    private String professorLastName;
}