package com.epam.springproject.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseDto {

    private String name;
    private String description;
    private String materials;
    private String task;
    private String duration;
    private long courseId;
    private long teacherId;

}
