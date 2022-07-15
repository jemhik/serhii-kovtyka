package com.epam.springproject.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class User {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private Status status;

}
