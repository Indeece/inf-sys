package ru.indeece.infsys.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.indeece.infsys.dto.ProfessorDto;
import ru.indeece.infsys.entities.Professor;
import ru.indeece.infsys.mappers.ProfessorMapper;
import ru.indeece.infsys.repository.ProfessorRepository;

@Service
@Slf4j
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final ProfessorMapper professorMapper;

    public ProfessorService(ProfessorRepository professorRepository, ProfessorMapper professorMapper) {
        this.professorRepository = professorRepository;
        this.professorMapper = professorMapper;
    }

    @Cacheable(value = "professors", key = "#id", unless = "#result == null")
    public ProfessorDto getProfessor(Long id) {
        log.info("Get professor by id: {}", id);
        Professor professor = professorRepository.findByIdWithRelations(id).orElseThrow(() -> {
            log.warn("Professor with id={} was not found", id);
            return new EntityNotFoundException("Professor not found - " + id);
        });
        return professorMapper.toDto(professor);
    }

    @CacheEvict(value = "professors", allEntries = true)
    public ProfessorDto saveProfessor(Professor professor) {
        log.info("Saving professor with email: {}", professor.getEmail());
        Professor savedProfessor = professorRepository.save(professor);
        log.info("Saved professor with id: {}", savedProfessor.getId());
        return professorMapper.toDto(savedProfessor);
    }

    @CacheEvict(value = "professors", allEntries = true)
    public ProfessorDto updateProfessor(Professor professor) {
        log.info("Updating professor with id: {}", professor.getId());
        if (!professorRepository.existsById(professor.getId())) {
            throw new EntityNotFoundException("Professor not found - " + professor.getId());
        }
        Professor updatedProfessor = professorRepository.save(professor);
        log.info("Updated professor with id: {}", updatedProfessor.getId());
        return professorMapper.toDto(updatedProfessor);
    }

    @CacheEvict(value = "professors", allEntries = true)
    public void deleteProfessor(Long id) {
        log.info("Deleting professor with id: {}", id);
        if (!professorRepository.existsById(id)) {
            throw new EntityNotFoundException("Professor not found - " + id);
        }
        professorRepository.deleteById(id);
        log.info("Deleted professor with id: {}", id);
    }
}