package com.epam.springproject.dto;

import com.epam.springproject.dto.group.OnCreate;
import com.epam.springproject.dto.group.OnUpdate;
import com.epam.springproject.model.Course;
import com.epam.springproject.model.Role;
import com.epam.springproject.model.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private long id;

    @NotBlank(message = "'firstName shouldn't be empty", groups = OnCreate.class)
    private String firstName;

    @NotBlank(message = "'lastName shouldn't be empty", groups = OnCreate.class)
    private String lastName;

    @Email
    @Null(message = "'email' should be absent in request", groups = OnUpdate.class)
    @NotBlank(message = "'email shouldn't be empty", groups = OnCreate.class)
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
            message = "password should only contain at least 1 big letter, 1 numbers and at least 8 letters ")
    @NotBlank(message = "'password shouldn't be empty", groups = OnCreate.class)
    @Null(message = "'password' should be absent in request", groups = OnUpdate.class)
    private String password;
    private Role role;
    private Status status;
    private List<Course> courses;

}
