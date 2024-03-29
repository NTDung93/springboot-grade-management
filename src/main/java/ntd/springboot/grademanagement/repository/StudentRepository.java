package ntd.springboot.grademanagement.repository;

import ntd.springboot.grademanagement.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneContaining(String name, String email, String phone);
    Student findByNameIgnoreCase(String name);
}
