package ntd.springboot.grademanagement.repository;

import ntd.springboot.grademanagement.model.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByNameContainingIgnoreCase(String keyword);
    Subject findByNameIgnoreCase(String name);
}
