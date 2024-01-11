package ntd.springboot.grademanagement.model.payload;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeDto {
    private Long studentId;
    private Long subjectId;

    @DecimalMin(value = "0.0", inclusive = true, message = "Grade must be greater than or equal to 0")
    @DecimalMax(value = "10.0", inclusive = true, message = "Grade must be less than or equal to 10")
    private Float grade;
}
