package ntd.springboot.grademanagement.service.impl;

import ntd.springboot.grademanagement.model.entity.Grade;
import ntd.springboot.grademanagement.model.entity.Student;
import ntd.springboot.grademanagement.model.entity.Subject;
import ntd.springboot.grademanagement.model.exception.ResourceNotFoundException;
import ntd.springboot.grademanagement.model.payload.GradeDto;
import ntd.springboot.grademanagement.model.payload.GradeResponse;
import ntd.springboot.grademanagement.repository.GradeRepository;
import ntd.springboot.grademanagement.repository.StudentRepository;
import ntd.springboot.grademanagement.repository.SubjectRepository;
import ntd.springboot.grademanagement.service.GradeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradeServiceImpl implements GradeService {
    private GradeRepository gradeRepository;
    private StudentRepository studentRepository;
    private SubjectRepository subjectRepository;
    private ModelMapper mapper;

    @Autowired
    public GradeServiceImpl(GradeRepository gradeRepository, StudentRepository studentRepository, SubjectRepository subjectRepository, ModelMapper mapper) {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.mapper = mapper;
    }

    @Override
    public GradeDto addGrade(GradeDto gradeDto) {
        Student student = studentRepository.findById(gradeDto.getStudentId()).orElseThrow(() -> new ResourceNotFoundException("Student", "id", gradeDto.getStudentId()));
        Subject subject = subjectRepository.findById(gradeDto.getSubjectId()).orElseThrow(() -> new ResourceNotFoundException("Subject", "id", gradeDto.getSubjectId()));
        if (student != null && subject != null) {
            Grade grade = mapper.map(gradeDto, Grade.class);
            return mapper.map(gradeRepository.save(grade), GradeDto.class);
        }
        return null;
    }

    @Override
    public GradeResponse getAllGrades(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        //create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Grade> grades = gradeRepository.findAll(pageable);

        //get content of page
        List<Grade> gradeList = grades.getContent();

        //format the response
        List<GradeDto> content = gradeList.stream().map(grade -> mapper.map(grade, GradeDto.class)).collect(Collectors.toList());
        GradeResponse gradeResponse = new GradeResponse();
        gradeResponse.setContent(content);
        gradeResponse.setPageNo(grades.getNumber());
        gradeResponse.setPageSize(grades.getSize());
        gradeResponse.setTotalElements(grades.getTotalElements());
        gradeResponse.setTotalPages(grades.getTotalPages());
        gradeResponse.setLast(grades.isLast());

        return gradeResponse;
    }

    @Override
    public GradeDto getGradeById(Long studentId, Long subjectId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new ResourceNotFoundException("Subject", "id", subjectId));
        Grade grade = gradeRepository.findByStudentIdAndSubjectId(studentId, subjectId);
        if (grade == null) {
            throw new ResourceNotFoundException("Grade");
        }
        if (student != null && subject != null && grade != null) {
            return mapper.map(grade, GradeDto.class);
        }
        return null;
    }

    @Override
    public GradeDto updateGrade(GradeDto gradeDto, Long studentId, Long subjectId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new ResourceNotFoundException("Subject", "id", subjectId));
        Grade grade = gradeRepository.findByStudentIdAndSubjectId(studentId, subjectId);
        if (grade == null) {
            throw new ResourceNotFoundException("Grade");
        }
        if (student != null && subject != null && grade != null) {
            grade.setGrade(gradeDto.getGrade());
            gradeRepository.save(grade);
            return mapper.map(grade, GradeDto.class);
        }
        return null;
    }

    @Override
    public void deleteGrade(Long studentId, Long subjectId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new ResourceNotFoundException("Subject", "id", subjectId));
        Grade grade = gradeRepository.findByStudentIdAndSubjectId(studentId, subjectId);
        if (grade == null) {
            throw new ResourceNotFoundException("Grade");
        }
        if (student != null && subject != null && grade != null) {
            gradeRepository.delete(grade);
        }
    }

    @Override
    public List<GradeDto> searchGrade(String studentName, String subjectName) {
        //find grade by student name or subject name or by both
        if (studentName != null && subjectName != null) {
            Student student = studentRepository.findByNameIgnoreCase(studentName);
            if (student == null) {
                throw new ResourceNotFoundException("Student");
            }
            Subject subject = subjectRepository.findByNameIgnoreCase(subjectName);
            if (subject == null) {
                throw new ResourceNotFoundException("Subject");
            }
            if (student != null && subject != null) {
                Grade grade = gradeRepository.findByStudentIdAndSubjectId(student.getId(), subject.getId());
                if (grade == null) {
                    throw new ResourceNotFoundException("Grade");
                }
                List<GradeDto> list = new ArrayList<>();
                list.add(mapper.map(grade, GradeDto.class));
                return list;
            }
        } else if (studentName != null) {
            Student student = studentRepository.findByNameIgnoreCase(studentName);
            if (student != null) {
                List<Grade> grades = gradeRepository.findByStudentId(student.getId());
                if (grades.isEmpty()) {
                    throw new ResourceNotFoundException("Grade");
                } else {
                    return grades.stream().map(grade -> mapper.map(grade, GradeDto.class)).collect(Collectors.toList());
                }
            } else {
                throw new ResourceNotFoundException("Student");
            }
        } else if (subjectName != null) {
            Subject subject = subjectRepository.findByNameIgnoreCase(subjectName);
            if (subject != null) {
                List<Grade> grades = gradeRepository.findBySubjectId(subject.getId());
                if (grades.isEmpty()) {
                    throw new ResourceNotFoundException("Grade");
                } else {
                    return grades.stream().map(grade -> mapper.map(grade, GradeDto.class)).collect(Collectors.toList());
                }
            } else {
                throw new ResourceNotFoundException("Subject");
            }
        } else {
            return gradeRepository.findAll().stream().map(grade -> mapper.map(grade, GradeDto.class)).collect(Collectors.toList());
        }
        return null;
    }
}
