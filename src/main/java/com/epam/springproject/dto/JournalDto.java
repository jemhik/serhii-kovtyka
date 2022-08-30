package com.epam.springproject.dto;

import com.epam.springproject.model.StudentCourse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JournalDto {

    private Long assessmentId;

    private StudentCourse studentCourse;

    private Long assessment;
}
