package ntd.springboot.grademanagement.controller;

import jakarta.validation.Valid;
import ntd.springboot.grademanagement.model.payload.StudentResponse;
import ntd.springboot.grademanagement.model.payload.SubjectDto;
import ntd.springboot.grademanagement.model.payload.SubjectResponse;
import ntd.springboot.grademanagement.service.SubjectService;
import ntd.springboot.grademanagement.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/subjects")
public class SubjectController {
    private SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    public ResponseEntity<SubjectDto> createSubject(@Valid @RequestBody SubjectDto subjectDto){
        return new ResponseEntity<>(subjectService.createSubject(subjectDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<SubjectResponse> getAllSubjects(
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return ResponseEntity.ok(subjectService.getAllSubject(pageNo, pageSize, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDto> getSubjectById(@PathVariable Long id){
        return ResponseEntity.ok(subjectService.getSubjectById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectDto> updateSubject(@Valid @RequestBody SubjectDto subjectDto, @PathVariable Long id){
        return ResponseEntity.ok(subjectService.updateSubject(subjectDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubject(@PathVariable Long id){
        subjectService.deleteSubject(id);
        return ResponseEntity.ok("Delete subject successfully");
    }

    @GetMapping("/search")
    public ResponseEntity<List<SubjectDto>> searchSubject(@RequestParam(name = "searchVal") String keyword){
        return ResponseEntity.ok(subjectService.searchSubject(keyword));
    }
}
