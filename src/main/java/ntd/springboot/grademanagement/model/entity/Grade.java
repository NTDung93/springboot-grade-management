package ntd.springboot.grademanagement.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "grades")
public class Grade {
    @EmbeddedId
    private GradeKey id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    Student student;

    @ManyToOne
    @MapsId("subjectId")
    @JoinColumn(name = "subject_id")
    Subject subject;

    @Column(nullable = false)
    private Float grade;
}
