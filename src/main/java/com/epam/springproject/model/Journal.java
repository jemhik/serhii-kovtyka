package com.epam.springproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Journal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assessmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="student_course_id", referencedColumnName = "studentCourseId")
    @JsonIgnore
    private StudentCourse studentCourse;

    private Long assessment;

}
