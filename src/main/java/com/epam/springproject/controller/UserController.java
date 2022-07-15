package com.epam.springproject.controller;


import com.epam.springproject.api.UserApi;
import com.epam.springproject.dto.UserDto;
import com.epam.springproject.dto.group.OnCreate;
import com.epam.springproject.dto.group.OnUpdate;
import com.epam.springproject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public List<UserDto> getAllUsers() {
        log.info("CourseController getAllUsers method");
        return userService.listUsers();
    }

    @Override
    public UserDto getUser(String email) {
        log.info("CourseController getUser with email " + email);
        return userService.getUser(email);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("CourseController getUser with email " + userDto.getEmail());
        return userService.createUser(userDto);
    }

    @Override
    public UserDto updateUser(String email, UserDto userDto) {
        log.info("CourseController getUser with email " + email);
        return userService.updateUser(email, userDto);
    }

    @Override
    public ResponseEntity<Void> deleteUser(String email) {
        log.info("CourseController getUser with email " + email);
        userService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }

}
