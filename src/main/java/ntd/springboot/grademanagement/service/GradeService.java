package ntd.springboot.grademanagement.service;

import ntd.springboot.grademanagement.model.payload.GradeDto;
import ntd.springboot.grademanagement.model.payload.GradeResponse;
import ntd.springboot.grademanagement.service.impl.GradeServiceImpl;

import java.util.List;

public interface GradeService {
    GradeDto addGrade(GradeDto gradeDto);
    GradeResponse getAllGrades(int pageNo, int pageSize, String sortBy, String sortDir);
    GradeDto getGradeById(Long studentId, Long subjectId);
    GradeDto updateGrade(GradeDto gradeDto, Long studentId, Long subjectId);
    void deleteGrade(Long studentId, Long subjectId);
    List<GradeDto> searchGrade(String studentName, String subjectName);
}
