package ntd.springboot.grademanagement.model.payload;

import lombok.Data;

@Data
public class GradeDto {
    private Long studentId;
    private Long subjectId;
    private Float grade;
}
