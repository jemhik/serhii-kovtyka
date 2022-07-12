package com.epam.springproject.dto;

import com.epam.springproject.model.Course;
import com.epam.springproject.model.Role;
import com.epam.springproject.model.Status;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDto {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String repeatPassword;
    private Role role;
    private Status status;
    private List<Course> courses;

}
