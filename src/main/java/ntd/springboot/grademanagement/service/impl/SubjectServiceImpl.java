package ntd.springboot.grademanagement.service.impl;

import ntd.springboot.grademanagement.model.entity.Subject;
import ntd.springboot.grademanagement.model.exception.ResourceNotFoundException;
import ntd.springboot.grademanagement.model.payload.SubjectDto;
import ntd.springboot.grademanagement.model.payload.SubjectResponse;
import ntd.springboot.grademanagement.repository.SubjectRepository;
import ntd.springboot.grademanagement.service.SubjectService;
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
public class SubjectServiceImpl implements SubjectService {
    private SubjectRepository subjectRepository;
    private ModelMapper mapper;

    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository, ModelMapper mapper) {
        this.subjectRepository = subjectRepository;
        this.mapper = mapper;
    }

    @Override
    public SubjectDto createSubject(SubjectDto subjectDto) {
        Subject subject = mapper.map(subjectDto, Subject.class);
        return mapper.map(subjectRepository.save(subject), SubjectDto.class);
    }

    @Override
    public SubjectResponse getAllSubject(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        //create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Subject> subjects = subjectRepository.findAll(pageable);

        //get content of page
        List<Subject> subjectList = subjects.getContent();

        //format the response
        List<SubjectDto> content = subjectList.stream().map(subject -> mapper.map(subject, SubjectDto.class)).collect(Collectors.toList());
        SubjectResponse subjectResponse = new SubjectResponse();
        subjectResponse.setContent(content);
        subjectResponse.setPageNo(subjects.getNumber());
        subjectResponse.setPageSize(subjects.getSize());
        subjectResponse.setTotalElements(subjects.getTotalElements());
        subjectResponse.setTotalPages(subjects.getTotalPages());
        subjectResponse.setLast(subjects.isLast());

        return subjectResponse;
    }

    @Override
    public SubjectDto getSubjectById(Long id) {
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Subject", "id", id));
        return mapper.map(subject, SubjectDto.class);
    }

    @Override
    public SubjectDto updateSubject(SubjectDto subjectDto, Long id) {
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Subject", "id", id));
        subject.setName(subjectDto.getName());
        return mapper.map(subjectRepository.save(subject), SubjectDto.class);
    }

    @Override
    public void deleteSubject(Long id) {
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Subject", "id", id));
        subjectRepository.delete(subject);
    }

    @Override
    public List<SubjectDto> searchSubject(String keyword) {
        List<Subject> subjects = subjectRepository.findByNameContainingIgnoreCase(keyword);
        if (!subjects.isEmpty()) {
            return subjects.stream().map(subject -> mapper.map(subject, SubjectDto.class)).collect(Collectors.toList());
        }else {
            throw new ResourceNotFoundException("Subject");
        }
    }
}
