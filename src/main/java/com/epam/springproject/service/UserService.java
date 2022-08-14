package com.epam.springproject.service;

import com.epam.springproject.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    UserDto getUser(String email);

    UserDto createUser(UserDto userDto);

    UserDto updateUser(String email, UserDto userDto);

    void deleteUser(String email);

    Page<UserDto> listUsers(Pageable pageable);
}
