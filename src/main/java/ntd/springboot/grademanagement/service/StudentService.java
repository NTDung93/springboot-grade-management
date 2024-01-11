package ntd.springboot.grademanagement.service;

import ntd.springboot.grademanagement.model.payload.StudentDto;
import ntd.springboot.grademanagement.model.payload.StudentResponse;

import java.util.List;

public interface StudentService {
    StudentDto createStudent(StudentDto studentDto);
    StudentResponse getAllStudents(int pageNo, int pageSize, String sortBy, String sortDir);
    StudentDto getStudentById(Long id);
    StudentDto updateStudent(StudentDto studentDto, Long id);
    void deleteStudent(Long id);
    List<StudentDto> searchStudent(String searchVal);
}
