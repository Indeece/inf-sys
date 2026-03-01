package ru.indeece.infsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.indeece.infsys.entities.Group;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByCode(String code);
    boolean existsByCode(String code);

    @Query("SELECT COUNT(s) FROM Student s WHERE s.group.id = :groupId")
    int countStudentsByGroupId(@Param("groupId") Long groupId);
}