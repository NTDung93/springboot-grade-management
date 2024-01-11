package ntd.springboot.grademanagement.service.impl;

import ntd.springboot.grademanagement.model.entity.Student;
import ntd.springboot.grademanagement.model.exception.ResourceNotFoundException;
import ntd.springboot.grademanagement.model.payload.StudentDto;
import ntd.springboot.grademanagement.model.payload.StudentResponse;
import ntd.springboot.grademanagement.repository.StudentRepository;
import ntd.springboot.grademanagement.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;
    private ModelMapper mapper;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, ModelMapper mapper) {
        this.studentRepository = studentRepository;
        this.mapper = mapper;
    }

    @Override
    public StudentResponse getAllStudents(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        //create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Student> students = studentRepository.findAll(pageable);

        //get content of page
        List<Student> studentList = students.getContent();

        //format the response
        List<StudentDto> content = studentList.stream().map(student -> mapper.map(student, StudentDto.class)).collect(Collectors.toList());
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setContent(content);
        studentResponse.setPageNo(students.getNumber());
        studentResponse.setPageSize(students.getSize());
        studentResponse.setTotalElements(students.getTotalElements());
        studentResponse.setTotalPages(students.getTotalPages());
        studentResponse.setLast(students.isLast());

        return studentResponse;
    }

    @Override
    public StudentDto createStudent(StudentDto studentDto) {
        Student student = mapper.map(studentDto, Student.class);
        return mapper.map(studentRepository.save(student), StudentDto.class);
    }

    @Override
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
        return mapper.map(student, StudentDto.class);
    }

    @Override
    public StudentDto updateStudent(StudentDto studentDto, Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
        student.setName(studentDto.getName());
        student.setPhone(studentDto.getPhone());
        student.setEmail(studentDto.getEmail());
        return mapper.map(studentRepository.save(student), StudentDto.class);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
        studentRepository.delete(student);
    }

    @Override
    public List<StudentDto> searchStudent(String searchVal) {
         List<Student> students = studentRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneContaining(searchVal, searchVal, searchVal);
         if (students.isEmpty()) {
             throw new ResourceNotFoundException("Student");
         }else {
             return students.stream().map(student -> mapper.map(student, StudentDto.class)).collect(Collectors.toList());
         }
    }
}
