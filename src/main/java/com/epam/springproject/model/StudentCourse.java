package com.epam.springproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentCourseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id", referencedColumnName = "courseId")
    @JsonIgnore
    private Course course;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="student_id", referencedColumnName = "id")
    @JsonIgnore
    private User student;
    private String progress;
    private String studentSolution;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "studentCourse", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Journal> assessments;

}
