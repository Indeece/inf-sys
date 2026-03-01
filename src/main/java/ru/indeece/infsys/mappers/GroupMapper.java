package ru.indeece.infsys.mappers;

import org.springframework.stereotype.Component;
import ru.indeece.infsys.dto.GroupDto;
import ru.indeece.infsys.entities.Group;
import ru.indeece.infsys.repository.GroupRepository;

@Component
public class GroupMapper {

    private final GroupRepository groupRepository;

    public GroupMapper(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public GroupDto toDto(Group group) {
        if (group == null) return null;

        return GroupDto.builder()
                .id(group.getId())
                .code(group.getCode())
                .studentsCount(groupRepository.countStudentsByGroupId(group.getId()))
                .build();
    }
}