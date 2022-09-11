package com.epam.springproject.service.impl;

import com.epam.springproject.dto.UserDto;
import com.epam.springproject.mapper.UserMapper;
import com.epam.springproject.model.User;
import com.epam.springproject.repository.UserRepository;
import com.epam.springproject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto getUser(String email) {
        log.info("UserService getUser by email " + email);
        User user = userRepository.getUser(email);
        return UserMapper.INSTANCE.mapUserToUserDto(user);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("UserService createUser with email " + userDto.getEmail());
        User user = UserMapper.INSTANCE.mapUserDtoToUser(userDto);
        user = userRepository.createUser(user);
        return UserMapper.INSTANCE.mapUserToUserDto(user);
    }

    @Override
    public UserDto updateUser(String email, UserDto userDto) {
        log.info("UserService updateUser with email " + email);
        User user = UserMapper.INSTANCE.mapUserDtoToUser(userDto);

        User oldUser = userRepository.getUser(email);
        user.setEmail(oldUser.getEmail());
        user.setPassword(oldUser.getPassword());

        user = userRepository.updateUser(email, user);
        return UserMapper.INSTANCE.mapUserToUserDto(user);
    }

    @Override
    public void deleteUser(String email) {
        log.info("UserService deleteUser with email " + email);
        userRepository.deleteUser(email);
    }

    @Override
    public List<UserDto> listUsers() {
        log.info("UserService listUsers method");
        return userRepository.listUsers()
                .stream()
                .map(UserMapper.INSTANCE::mapUserToUserDto)
                .collect(Collectors.toList());
    }
}
