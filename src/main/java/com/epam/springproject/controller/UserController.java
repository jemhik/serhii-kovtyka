package com.epam.springproject.controller;


import com.epam.springproject.api.UserApi;
import com.epam.springproject.dto.UserDto;
import com.epam.springproject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public Page<UserDto> getAllUsers(int pageSize, int pageNumber, String sortType) {
        log.info("UserController getAllUsers method");
        return userService.listUsers(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.DEFAULT_DIRECTION, sortType)));
    }

    @Override
    public UserDto getUser(String email) {
        log.info("UserController getUser with email " + email);
        return userService.getUser(email);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("UserController getUser with email " + userDto.getEmail());
        return userService.createUser(userDto);
    }

    @Override
    public UserDto updateUser(String email, UserDto userDto) {
        log.info("UserController getUser with email " + email);
        return userService.updateUser(email, userDto);
    }

    @Override
    public ResponseEntity<Void> deleteUser(String email) {
        log.info("UserController getUser with email " + email);
        userService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }

}
