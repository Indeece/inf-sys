package ru.indeece.infsys.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.indeece.infsys.dto.DisciplineDto;
import ru.indeece.infsys.entities.Discipline;
import ru.indeece.infsys.mappers.DisciplineMapper;
import ru.indeece.infsys.repository.DisciplineRepository;

@Service
@Slf4j
public class DisciplineService {

    private final DisciplineRepository disciplineRepository;
    private final DisciplineMapper disciplineMapper;

    public DisciplineService(DisciplineRepository disciplineRepository, DisciplineMapper disciplineMapper) {
        this.disciplineRepository = disciplineRepository;
        this.disciplineMapper = disciplineMapper;
    }

    @Cacheable(value = "disciplines", key = "#id", unless = "#result == null")
    public DisciplineDto getDiscipline(Long id) {
        log.info("Get discipline by id: {}", id);
        Discipline discipline = disciplineRepository.findByIdWithDepartment(id).orElseThrow(() -> {
            log.warn("Discipline with id={} was not found", id);
            return new EntityNotFoundException("Discipline not found - " + id);
        });
        return disciplineMapper.toDto(discipline);
    }

    @Cacheable(value = "disciplines", key = "'name_' + #name", unless = "#result == null")
    public DisciplineDto getDisciplineByName(String name) {
        log.info("Get discipline by name: {}", name);
        Discipline discipline = disciplineRepository.findByName(name).orElseThrow(() -> {
            log.warn("Discipline with name={} was not found", name);
            return new EntityNotFoundException("Discipline not found - " + name);
        });
        return disciplineMapper.toDto(discipline);
    }

    @CacheEvict(value = "disciplines", allEntries = true)
    public DisciplineDto saveDiscipline(Discipline discipline) {
        log.info("Saving discipline with name: {}", discipline.getName());
        Discipline savedDiscipline = disciplineRepository.save(discipline);
        log.info("Saved discipline with id: {}", savedDiscipline.getId());
        return disciplineMapper.toDto(savedDiscipline);
    }

    @CacheEvict(value = "disciplines", allEntries = true)
    public DisciplineDto updateDiscipline(Discipline discipline) {
        log.info("Updating discipline with id: {}", discipline.getId());
        if (!disciplineRepository.existsById(discipline.getId())) {
            throw new EntityNotFoundException("Discipline not found - " + discipline.getId());
        }
        Discipline updatedDiscipline = disciplineRepository.save(discipline);
        log.info("Updated discipline with id: {}", updatedDiscipline.getId());
        return disciplineMapper.toDto(updatedDiscipline);
    }

    @CacheEvict(value = "disciplines", allEntries = true)
    public void deleteDiscipline(Long id) {
        log.info("Deleting discipline with id: {}", id);
        if (!disciplineRepository.existsById(id)) {
            throw new EntityNotFoundException("Discipline not found - " + id);
        }
        disciplineRepository.deleteById(id);
        log.info("Deleted discipline with id: {}", id);
    }
}