package ntd.springboot.grademanagement.model.payload;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import ntd.springboot.grademanagement.model.entity.Grade;

import java.util.Set;

@Data
public class StudentDto {
    private Long id;

    @NotEmpty(message = "Name should not be empty!")
    private String name;

    @Pattern(regexp="(^$|[0-9]{10,11})", message = "Phone number must be 10 or 11 digits!")
    private String phone;

    @Email(regexp = ".+@.+\\..+", message = "Email is invalid!")
    private String email;

//    private Set<Grade> grades;
}
