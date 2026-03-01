package ru.indeece.infsys.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ProfessorDto", description = "Professor data transfer object")
public class ProfessorDto implements Serializable {
    private static final long serialVersionUID = 102L;

    @Schema(description = "Professor ID", example = "1")
    private Long id;

    @Schema(description = "First name", example = "Иван")
    private String firstName;

    @Schema(description = "Last name", example = "Петров")
    private String lastName;

    @Schema(description = "Hire date", example = "2020-09-01")
    private LocalDate hireDate;

    @Schema(description = "Email", example = "petrov@university.ru")
    private String email;

    @Schema(description = "Department ID", example = "1")
    private Long departmentId;

    @Schema(description = "Department name", example = "Кафедра информатики")
    private String departmentName;

    @Schema(description = "Department code", example = "CS")
    private String departmentCode;

    @Schema(description = "List of discipline IDs", example = "[1, 2, 3]")
    private List<Long> disciplineIds;

    @Schema(description = "List of discipline names", example = "[\"Математика\", \"Физика\"]")
    private List<String> disciplineNames;
}