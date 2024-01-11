package ntd.springboot.grademanagement.controller;

import jakarta.validation.Valid;
import ntd.springboot.grademanagement.model.payload.GradeDto;
import ntd.springboot.grademanagement.model.payload.GradeResponse;
import ntd.springboot.grademanagement.service.GradeService;
import ntd.springboot.grademanagement.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/grade")
public class GradeController {
    private GradeService gradeService;

    @Autowired
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<GradeDto> addGrade(@Valid @RequestBody GradeDto gradeDto) {
        return ResponseEntity.ok(gradeService.addGrade(gradeDto));
    }

    @GetMapping
    public ResponseEntity<GradeResponse> getAllGrades(
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return ResponseEntity.ok(gradeService.getAllGrades(pageNo, pageSize, sortBy, sortDir));
    }

    @GetMapping("/{studentId}/{subjectId}")
    public ResponseEntity<GradeDto> getGradeById(@PathVariable(name = "studentId") Long studentId, @PathVariable(name = "subjectId") Long subjectId) {
        return ResponseEntity.ok(gradeService.getGradeById(studentId, subjectId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{studentId}/{subjectId}")
    public ResponseEntity<GradeDto> updateGrade(@Valid @RequestBody GradeDto gradeDto, @PathVariable(name = "studentId") Long studentId, @PathVariable(name = "subjectId") Long subjectId) {
        return ResponseEntity.ok(gradeService.updateGrade(gradeDto, studentId, subjectId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{studentId}/{subjectId}")
    public ResponseEntity<String> deleteGrade(@PathVariable(name = "studentId") Long studentId, @PathVariable(name = "subjectId") Long subjectId) {
        gradeService.deleteGrade(studentId, subjectId);
        return ResponseEntity.ok("Delete grade successfully!");
    }

    @GetMapping("search")
    public ResponseEntity<List<GradeDto>> searchGrade(@RequestParam(required = false) String studentName, @RequestParam(required = false) String subjectName) {
        return ResponseEntity.ok(gradeService.searchGrade(studentName, subjectName));
    }
}
