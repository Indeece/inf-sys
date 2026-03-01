package ru.indeece.infsys.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.indeece.infsys.dto.GroupDto;
import ru.indeece.infsys.entities.Group;
import ru.indeece.infsys.service.GroupService;

@RestController
@RequestMapping("/api/v1/group")
@Tag(name = "Group service", description = "CRUD operations for groups")
@Slf4j
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/add")
    @Operation(summary = "Add group", description = "Adds a new group")
    public ResponseEntity<GroupDto> addGroup(@RequestBody Group group) {
        log.info("POST /api/v1/group/add | code: {}", group.getCode());
        return ResponseEntity.ok(groupService.saveGroup(group));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get group", description = "Returns a group by id")
    public ResponseEntity<GroupDto> getGroup(@PathVariable Long id) {
        log.info("GET /api/v1/group/{}", id);
        return ResponseEntity.ok(groupService.getGroup(id));
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Get group by code", description = "Returns a group by its code")
    public ResponseEntity<GroupDto> getGroupByCode(@PathVariable String code) {
        log.info("GET /api/v1/group/code/{}", code);
        return ResponseEntity.ok(groupService.getGroupByCode(code));
    }

    @PutMapping("/update")
    @Operation(summary = "Update group", description = "Updates group's info")
    public ResponseEntity<GroupDto> updateGroup(@RequestBody Group group) {
        log.info("PUT /api/v1/group/update | id: {}", group.getId());
        return ResponseEntity.ok(groupService.updateGroup(group));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete group", description = "Deletes group from DB")
    public ResponseEntity<Void> deleteGroup(@RequestParam Long id) {
        log.info("DELETE /api/v1/group/delete | id: {}", id);
        groupService.deleteGroup(id);
        return ResponseEntity.ok().build();
    }
}