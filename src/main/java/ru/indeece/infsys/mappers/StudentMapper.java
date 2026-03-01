package ru.indeece.infsys.mappers;

import org.springframework.stereotype.Component;
import ru.indeece.infsys.dto.StudentDto;
import ru.indeece.infsys.entities.Student;

@Component
public class StudentMapper {

    public StudentDto toDto(Student student) {
        if (student == null) return null;

        StudentDto.StudentDtoBuilder builder = StudentDto.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .dateOfBirth(student.getDateOfBirth())
                .email(student.getEmail());

        if (student.getGroup() != null) {
            builder.groupId(student.getGroup().getId())
                    .groupCode(student.getGroup().getCode());
        }

        return builder.build();
    }
}