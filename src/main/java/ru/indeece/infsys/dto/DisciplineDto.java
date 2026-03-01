package ru.indeece.infsys.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.indeece.infsys.enums.ExamType;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "DisciplineDto", description = "Discipline data transfer object")
public class DisciplineDto implements Serializable {
    private static final long serialVersionUID = 104L;

    @Schema(description = "Discipline ID", example = "1")
    private Long id;

    @Schema(description = "Discipline name", example = "Математический анализ")
    private String name;

    @Schema(description = "Total hours", example = "144")
    private Integer hours;

    @Schema(description = "Exam type", example = "EXAM")
    private ExamType examType;

    @Schema(description = "Department ID", example = "1")
    private Long departmentId;

    @Schema(description = "Department name", example = "Кафедра математики")
    private String departmentName;

    @Schema(description = "Number of professors", example = "5")
    private Integer professorsCount;
}