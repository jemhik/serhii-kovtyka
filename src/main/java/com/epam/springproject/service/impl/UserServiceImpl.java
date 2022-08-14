package com.epam.springproject.service.impl;

import com.epam.springproject.dto.UserDto;
import com.epam.springproject.exception.EntityNotFoundException;
import com.epam.springproject.exception.UserAlreadyExistsException;
import com.epam.springproject.mapper.UserMapper;
import com.epam.springproject.model.User;
import com.epam.springproject.repository.RoleRepository;
import com.epam.springproject.repository.StatusRepository;
import com.epam.springproject.repository.UserRepository;
import com.epam.springproject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String DEFAULT_USER_ROLE_NAME = "student";
    private static final String DEFAULT_USER_STATUS_NAME = "active";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StatusRepository statusRepository;

    @Override
    public UserDto getUser(String email) {
        log.info("UserService getUser by email " + email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        return UserMapper.INSTANCE.mapUserToUserDto(user);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("UserService createUser with email " + userDto.getEmail());
        if(userRepository.existsByEmail((userDto.getEmail()))) {
            throw new UserAlreadyExistsException();
        }
        userDto.setRole(roleRepository.findByName(DEFAULT_USER_ROLE_NAME).orElseThrow(EntityNotFoundException::new));
        userDto.setStatus(statusRepository.findByName(DEFAULT_USER_STATUS_NAME).orElseThrow(EntityNotFoundException::new));
        User user = UserMapper.INSTANCE.mapUserDtoToUser(userDto);
        user = userRepository.save(user);
        return UserMapper.INSTANCE.mapUserToUserDto(user);
    }

    @Override
    public UserDto updateUser(String email, UserDto userDto) {
        log.info("UserService updateUser with email " + email);
        User user = UserMapper.INSTANCE.mapUserDtoToUser(userDto);

        User oldUser = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        user.setEmail(oldUser.getEmail());
        user.setPassword(oldUser.getPassword());
        user.setId(oldUser.getId());

        user = userRepository.save(user);
        return UserMapper.INSTANCE.mapUserToUserDto(user);
    }

    @Override
    public void deleteUser(String email) {
        log.info("UserService deleteUser with email " + email);
        User user = userRepository.findByEmail(email)
                        .orElseThrow(EntityNotFoundException::new);
        userRepository.delete(user);
    }

    @Override
    public Page<UserDto> listUsers(Pageable pageable) {
        log.info("UserService listUsers method");
        return userRepository.findAll(pageable)
                .map(UserMapper.INSTANCE::mapUserToUserDto);
    }

//    private UserDto mapUserToUserDto(User user){
//        return UserDto.builder()
//                .firstName(user.getFirstName())
//                .lastName(user.getLastName())
//                .email(user.getEmail())
//                .build();
//    }
//
//    private User mapUserDtoToUser(UserDto userDto){
//        return User.builder()
//                .firstName(userDto.getFirstName())
//                .lastName(userDto.getLastName())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .build();
//    }
}
