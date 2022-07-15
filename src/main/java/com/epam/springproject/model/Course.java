package com.epam.springproject.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Course {

    private String name;
    private String description;
    private String materials;
    private String task;
    private String duration;
    private long courseId;
    private long teacherId;

}
