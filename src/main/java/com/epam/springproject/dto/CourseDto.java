package com.epam.springproject.dto;

import com.epam.springproject.dto.group.OnCreate;
import com.epam.springproject.validation.TextBoxConstraint;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class CourseDto {

    @NotBlank(message = "'name' shouldn't be empty", groups = OnCreate.class)
    private String name;

    @NotBlank(message = "'description' shouldn't be empty", groups = OnCreate.class)
    private String description;

    @NotBlank(message = "'materials' shouldn't be empty", groups = OnCreate.class)
    @TextBoxConstraint(message = "You should use only latin letters and numbers", groups = OnCreate.class)
    private String materials;

    @NotBlank(message = "'task' shouldn't be empty", groups = OnCreate.class)
    @TextBoxConstraint(message = "You should use only latin letters and numbers", groups = OnCreate.class)
    private String task;

    @NotBlank(message = "'duration' shouldn't be empty", groups = OnCreate.class)
    private String duration;

    private long courseId;
    private long teacherId;

}
