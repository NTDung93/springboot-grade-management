package ntd.springboot.grademanagement.repository;

import ntd.springboot.grademanagement.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
