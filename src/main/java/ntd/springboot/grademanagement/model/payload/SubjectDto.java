package ntd.springboot.grademanagement.model.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ntd.springboot.grademanagement.model.entity.Grade;

import java.util.Set;

@Data
public class SubjectDto {
    private Long id;

    @NotEmpty
    @Size(min = 3, message = "Subject name should have at least 3 characters")
    private String name;

    private Set<Grade> grades;
}
