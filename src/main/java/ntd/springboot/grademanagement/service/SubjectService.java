package ntd.springboot.grademanagement.service;

import ntd.springboot.grademanagement.model.payload.SubjectDto;
import ntd.springboot.grademanagement.model.payload.SubjectResponse;

import java.util.List;

public interface SubjectService {
    SubjectDto createSubject(SubjectDto subjectDto);
    SubjectResponse getAllSubject(int pageNo, int pageSize, String sortBy, String sortDir);
    SubjectDto getSubjectById(Long id);
    SubjectDto updateSubject(SubjectDto subjectDto, Long id);
    void deleteSubject(Long id);
    List<SubjectDto> searchSubject(String keyword);
}
