package ntd.springboot.grademanagement.repository;

import ntd.springboot.grademanagement.model.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    Grade findByStudentIdAndSubjectId(Long studentId, Long subjectId);
    List<Grade> findByStudentId(Long studentId);
    List<Grade> findBySubjectId(Long subjectId);
}
