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
@Schema(name = "GroupDto", description = "Group data transfer object")
public class GroupDto implements Serializable {
    private static final long serialVersionUID = 106L;

    @Schema(description = "Group ID", example = "1")
    private Long id;

    @Schema(description = "Group code", example = "ИС-21")
    private String code;

    @Schema(description = "Number of students", example = "25")
    private Integer studentsCount;
}