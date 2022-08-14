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
public class Course {

    private String name;
    private String description;
    private String materials;
    private String task;
    private String duration;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="teacherId", referencedColumnName = "id")
    @JsonIgnore
    private User teacher;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<StudentCourse> studentCourses;
}
