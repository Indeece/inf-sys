package ru.indeece.infsys.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.indeece.infsys.dto.LessonDto;
import ru.indeece.infsys.entities.Lesson;
import ru.indeece.infsys.service.LessonService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/lesson")
@Tag(name = "Lesson service", description = "Schedule management")
@Slf4j
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping("/add")
    @Operation(summary = "Add lesson", description = "Adds a new lesson to schedule")
    public ResponseEntity<LessonDto> addLesson(@RequestBody Lesson lesson) {
        log.info("POST /api/v1/lesson/add | group: {}, date: {}",
                lesson.getGroup().getCode(), lesson.getDate());
        return ResponseEntity.ok(lessonService.saveLesson(lesson));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get lesson", description = "Returns a lesson by id")
    public ResponseEntity<LessonDto> getLesson(@PathVariable Long id) {
        log.info("GET /api/v1/lesson/{}", id);
        return ResponseEntity.ok(lessonService.getLesson(id));
    }

    @GetMapping("/group/{groupId}/date/{date}")
    @Operation(summary = "Get lessons by group and date")
    public ResponseEntity<List<LessonDto>> getLessonsByGroupAndDate(
            @PathVariable Long groupId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("GET /api/v1/lesson/group/{}/date/{}", groupId, date);
        return ResponseEntity.ok(lessonService.getLessonsByGroupAndDate(groupId, date));
    }

    @GetMapping("/professor/{professorId}/date/{date}")
    @Operation(summary = "Get lessons by professor and date")
    public ResponseEntity<List<LessonDto>> getLessonsByProfessorAndDate(
            @PathVariable Long professorId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("GET /api/v1/lesson/professor/{}/date/{}", professorId, date);
        return ResponseEntity.ok(lessonService.getLessonsByProfessorAndDate(professorId, date));
    }

    @GetMapping("/group/{groupId}/week")
    @Operation(summary = "Get lessons for week")
    public ResponseEntity<List<LessonDto>> getLessonsForWeek(
            @PathVariable Long groupId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
        log.info("GET /api/v1/lesson/group/{}/week?startDate={}", groupId, startDate);
        return ResponseEntity.ok(lessonService.getLessonsByGroupForWeek(groupId, startDate));
    }

    @PutMapping("/update")
    @Operation(summary = "Update lesson", description = "Updates lesson's info")
    public ResponseEntity<LessonDto> updateLesson(@RequestBody Lesson lesson) {
        log.info("PUT /api/v1/lesson/update | id: {}", lesson.getId());
        return ResponseEntity.ok(lessonService.updateLesson(lesson));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete lesson", description = "Deletes lesson from schedule")
    public ResponseEntity<Void> deleteLesson(@RequestParam Long id) {
        log.info("DELETE /api/v1/lesson/delete | id: {}", id);
        lessonService.deleteLesson(id);
        return ResponseEntity.ok().build();
    }
}