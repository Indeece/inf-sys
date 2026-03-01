package ru.indeece.infsys.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.indeece.infsys.dto.LessonDto;
import ru.indeece.infsys.entities.Lesson;
import ru.indeece.infsys.mappers.LessonMapper;
import ru.indeece.infsys.repository.LessonRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;

    public LessonService(LessonRepository lessonRepository, LessonMapper lessonMapper) {
        this.lessonRepository = lessonRepository;
        this.lessonMapper = lessonMapper;
    }

    @Cacheable(value = "lessons", key = "#id", unless = "#result == null")
    public LessonDto getLesson(Long id) {
        log.info("Get lesson by id: {}", id);
        Lesson lesson = lessonRepository.findByIdWithRelations(id).orElseThrow(() -> {
            log.warn("Lesson with id={} was not found", id);
            return new EntityNotFoundException("Lesson not found - " + id);
        });
        return lessonMapper.toDto(lesson);
    }

    @Cacheable(value = "lessons", key = "'group_' + #groupId + '_' + #date", unless = "#result == null")
    public List<LessonDto> getLessonsByGroupAndDate(Long groupId, LocalDate date) {
        log.info("Get lessons for group {} on date {}", groupId, date);
        return lessonRepository.findByGroupIdAndDate(groupId, date)
                .stream()
                .map(lessonMapper::toDto)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "lessons", key = "'professor_' + #professorId + '_' + #date", unless = "#result == null")
    public List<LessonDto> getLessonsByProfessorAndDate(Long professorId, LocalDate date) {
        log.info("Get lessons for professor {} on date {}", professorId, date);
        return lessonRepository.findByProfessorIdAndDate(professorId, date)
                .stream()
                .map(lessonMapper::toDto)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "lessons", key = "'group_' + #groupId + '_week_' + #startDate", unless = "#result == null")
    public List<LessonDto> getLessonsByGroupForWeek(Long groupId, LocalDate startDate) {
        log.info("Get lessons for group {} for week starting {}", groupId, startDate);
        LocalDate endDate = startDate.plusDays(6);
        return lessonRepository.findByGroupIdAndDateBetween(groupId, startDate, endDate)
                .stream()
                .map(lessonMapper::toDto)
                .collect(Collectors.toList());
    }

    @CacheEvict(value = "lessons", allEntries = true)
    public LessonDto saveLesson(Lesson lesson) {
        log.info("Saving lesson for group {} on {}",
                lesson.getGroup().getCode(), lesson.getDate());
        Lesson savedLesson = lessonRepository.save(lesson);
        log.info("Saved lesson with id: {}", savedLesson.getId());
        return lessonMapper.toDto(savedLesson);
    }

    @CacheEvict(value = "lessons", allEntries = true)
    public LessonDto updateLesson(Lesson lesson) {
        log.info("Updating lesson with id: {}", lesson.getId());
        if (!lessonRepository.existsById(lesson.getId())) {
            throw new EntityNotFoundException("Lesson not found - " + lesson.getId());
        }
        Lesson updatedLesson = lessonRepository.save(lesson);
        log.info("Updated lesson with id: {}", updatedLesson.getId());
        return lessonMapper.toDto(updatedLesson);
    }

    @CacheEvict(value = "lessons", allEntries = true)
    public void deleteLesson(Long id) {
        log.info("Deleting lesson with id: {}", id);
        if (!lessonRepository.existsById(id)) {
            throw new EntityNotFoundException("Lesson not found - " + id);
        }
        lessonRepository.deleteById(id);
        log.info("Deleted lesson with id: {}", id);
    }
}