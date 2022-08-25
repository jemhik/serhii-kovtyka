package com.epam.springproject.test.util;

import com.epam.springproject.dto.UserDto;
import com.epam.springproject.model.Role;
import com.epam.springproject.model.Status;
import com.epam.springproject.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserTestDataUtil {

    public static final Long ID = 1L;
    public static final String FIRST_NAME = "FirstName";
    public static final String LAST_NAME = "LastName";
    public static final String TEST_EMAIL = "test@test.com";
    public static final String DEFAULT_USER_ROLE = "student";
    public static final String DEFAULT_USER_STATUS = "active";
    public static final String TEACHER_USER_ROLE = "teacher";
    private static final String TEST_PASSWORD = "Test1234!";

    public static User createUser() {
        return User.builder()
                .id(ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .status(createDefaultStatus())
                .role(createDefaultRole())
                .build();
    }

    public static UserDto createUserDto() {
        return UserDto.builder()
                .id(ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .build();
    }

    public static Role createDefaultRole() {
        return Role.builder()
                .roleId(3L)
                .name(DEFAULT_USER_ROLE)
                .build();
    }

    public static Status createDefaultStatus() {
        return Status.builder()
                .statusId(1L)
                .name(DEFAULT_USER_STATUS)
                .build();
    }

    public static Role createTeacherRole() {
        return Role.builder()
                .roleId(2L)
                .name(TEACHER_USER_ROLE)
                .build();
    }

}
