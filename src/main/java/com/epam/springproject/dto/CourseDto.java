package com.epam.springproject.dto;

import com.epam.springproject.dto.group.OnCreate;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class CourseDto {

    @NotBlank(message = "'name' shouldn't be empty", groups = OnCreate.class)
    private String name;

    @NotBlank(message = "'description' shouldn't be empty", groups = OnCreate.class)
    private String description;

    @NotBlank(message = "'materials' shouldn't be empty", groups = OnCreate.class)
    @Pattern(regexp = "^[a-zA-Z ]+[1-9]*",
            message = "You should use only latin letters and numbers")
    private String materials;

    @NotBlank(message = "'task' shouldn't be empty", groups = OnCreate.class)
    @Pattern(regexp = "^[a-zA-Z ]+[1-9]*",
            message = "You should use only latin letters and numbers")
    private String task;

    @NotBlank(message = "'duration' shouldn't be empty", groups = OnCreate.class)
    private String duration;

    private long courseId;
    private long teacherId;

}
