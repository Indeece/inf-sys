package ru.indeece.infsys.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.indeece.infsys.dto.GroupDto;
import ru.indeece.infsys.entities.Group;
import ru.indeece.infsys.mappers.GroupMapper;
import ru.indeece.infsys.repository.GroupRepository;

@Service
@Slf4j
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    public GroupService(GroupRepository groupRepository, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
    }

    @Cacheable(value = "groups", key = "#id", unless = "#result == null")
    public GroupDto getGroup(Long id) {
        log.info("Get group by id: {}", id);
        Group group = groupRepository.findById(id).orElseThrow(() -> {
            log.warn("Group with id={} was not found", id);
            return new EntityNotFoundException("Group not found - " + id);
        });
        return groupMapper.toDto(group);
    }

    @Cacheable(value = "groups", key = "'code_' + #code", unless = "#result == null")
    public GroupDto getGroupByCode(String code) {
        log.info("Get group by code: {}", code);
        Group group = groupRepository.findByCode(code).orElseThrow(() -> {
            log.warn("Group with code={} was not found", code);
            return new EntityNotFoundException("Group not found - " + code);
        });
        return groupMapper.toDto(group);
    }

    @CacheEvict(value = "groups", allEntries = true)
    public GroupDto saveGroup(Group group) {
        log.info("Saving group with code: {}", group.getCode());
        Group savedGroup = groupRepository.save(group);
        log.info("Saved group with id: {}", savedGroup.getId());
        return groupMapper.toDto(savedGroup);
    }

    @CacheEvict(value = "groups", allEntries = true)
    public GroupDto updateGroup(Group group) {
        log.info("Updating group with id: {}", group.getId());
        if (!groupRepository.existsById(group.getId())) {
            throw new EntityNotFoundException("Group not found - " + group.getId());
        }
        Group updatedGroup = groupRepository.save(group);
        log.info("Updated group with id: {}", updatedGroup.getId());
        return groupMapper.toDto(updatedGroup);
    }

    @CacheEvict(value = "groups", allEntries = true)
    public void deleteGroup(Long id) {
        log.info("Deleting group with id: {}", id);
        if (!groupRepository.existsById(id)) {
            throw new EntityNotFoundException("Group not found - " + id);
        }
        groupRepository.deleteById(id);
        log.info("Deleted group with id: {}", id);
    }
}