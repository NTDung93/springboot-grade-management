package ntd.springboot.grademanagement.service;

import ntd.springboot.grademanagement.model.payload.StudentDto;

import java.util.List;

public interface StudentService {
    List<StudentDto> getAllStudents();
}
