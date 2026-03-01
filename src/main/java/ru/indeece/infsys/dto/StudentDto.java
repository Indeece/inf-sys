package ru.indeece.infsys.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "StudentDto", description = "Student data transfer object")
public class StudentDto implements Serializable {
    private static final long serialVersionUID = 101L;

    @Schema(description = "Student ID", example = "1")
    private Long id;

    @Schema(description = "First name", example = "Алексей")
    private String firstName;

    @Schema(description = "Last name", example = "Смирнов")
    private String lastName;

    @Schema(description = "Date of birth", example = "2002-05-15")
    private LocalDate dateOfBirth;

    @Schema(description = "Email", example = "smirnov@student.ru")
    private String email;

    @Schema(description = "Group ID", example = "1")
    private Long groupId;

    @Schema(description = "Group code", example = "ИС-21")
    private String groupCode;
}