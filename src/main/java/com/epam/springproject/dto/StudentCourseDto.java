package com.epam.springproject.dto;

import com.epam.springproject.dto.group.OnCreate;
import com.epam.springproject.dto.group.OnUpdate;
import com.epam.springproject.model.Course;
import com.epam.springproject.model.User;
import com.epam.springproject.validation.TextBoxConstraint;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Data
@Builder
public class StudentCourseDto {

    @Null(message = "'courseId' should be absent in request", groups = OnUpdate.class)
    private Long studentCourseId;
    private Course course;
    private User student;

    @NotBlank(message = "'progress' shouldn't be empty", groups = OnCreate.class)
    @Null(message = "'teacher' should be absent in request", groups = OnUpdate.class)
    private String progress;

//    @NotBlank(message = "'studentSolution' shouldn't be empty", groups = OnCreate.class)
//    @TextBoxConstraint(message = "You should use only latin letters and numbers", groups = OnCreate.class)
    private String studentSolution;

}
