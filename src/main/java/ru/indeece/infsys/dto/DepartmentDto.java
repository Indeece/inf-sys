package ru.indeece.infsys.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "DepartmentDto", description = "Department data transfer object")
public class DepartmentDto implements Serializable {
    private static final long serialVersionUID = 105L;

    @Schema(description = "Department ID", example = "1")
    private Long id;

    @Schema(description = "Department name", example = "Кафедра информатики")
    private String name;

    @Schema(description = "Department code", example = "CS")
    private String code;

    @Schema(description = "Email", example = "cs@university.ru")
    private String email;

    @Schema(description = "Number of professors", example = "12")
    private Integer professorsCount;
}