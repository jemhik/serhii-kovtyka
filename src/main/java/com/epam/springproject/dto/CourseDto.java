package com.epam.springproject.dto;

import com.epam.springproject.dto.group.OnCreate;
import com.epam.springproject.dto.group.OnUpdate;
import com.epam.springproject.model.User;
import com.epam.springproject.validation.TextBoxConstraint;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Data
@Builder
public class CourseDto {

    @Null(message = "'courseId' should be absent in request", groups = OnUpdate.class)
    private Long courseId;

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

    @Null(message = "'teacher' should be absent in request", groups = OnUpdate.class)
    private User teacher;

}
