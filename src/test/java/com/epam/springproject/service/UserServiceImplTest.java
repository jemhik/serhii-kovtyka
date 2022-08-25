package com.epam.springproject.service;

import com.epam.springproject.dto.UserDto;
import com.epam.springproject.exception.EntityNotFoundException;
import com.epam.springproject.exception.UserAlreadyExistsException;
import com.epam.springproject.mapper.UserMapper;
import com.epam.springproject.model.Role;
import com.epam.springproject.model.Status;
import com.epam.springproject.model.User;
import com.epam.springproject.repository.RoleRepository;
import com.epam.springproject.repository.StatusRepository;
import com.epam.springproject.repository.UserRepository;
import com.epam.springproject.service.impl.UserServiceImpl;
import com.epam.springproject.test.util.UserTestDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.epam.springproject.test.util.UserTestDataUtil.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private StatusRepository statusRepository;

    @Test
    void getUserTest() {
        User user = UserTestDataUtil.createUser();
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(user));

        UserDto userDto = userService.getUser(TEST_EMAIL);

        assertThat(userDto, allOf(
                hasProperty("id", equalTo(user.getId())),
                hasProperty("firstName", equalTo(user.getFirstName())),
                hasProperty("lastName", equalTo(user.getLastName())),
                hasProperty("email", equalTo(user.getEmail())),
                hasProperty("password", equalTo(user.getPassword()))
        ));
    }

    @Test
    void getUserEntityNotFoundTest() {
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.getUser(TEST_EMAIL));
    }

    @Test
    void createUserTest() {
        User testUser = UserTestDataUtil.createUser();
        UserDto testUserDto = UserTestDataUtil.createUserDto();
        Role role = UserTestDataUtil.createDefaultRole();
        Status status = UserTestDataUtil.createDefaultStatus();

        when(roleRepository.findByName(DEFAULT_USER_ROLE)).thenReturn(Optional.of(role));
        when(statusRepository.findByName(DEFAULT_USER_STATUS)).thenReturn(Optional.of(status));
        when(userRepository.save(testUser)).thenReturn(testUser);

        UserDto userDto = userService.createUser(testUserDto);

        assertThat(userDto, allOf(
                hasProperty("id", equalTo(testUser.getId())),
                hasProperty("firstName", equalTo(testUser.getFirstName())),
                hasProperty("lastName", equalTo(testUser.getLastName())),
                hasProperty("email", equalTo(testUser.getEmail())),
                hasProperty("password", equalTo(testUser.getPassword())),
                hasProperty("role", equalTo(testUser.getRole())),
                hasProperty("status", equalTo(testUser.getStatus()))
        ));
    }

    @Test
    void createUserIfUserExistsTest() {
        UserDto userDto = UserTestDataUtil.createUserDto();
        when(userRepository.existsByEmail(TEST_EMAIL)).thenReturn(true);
        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(userDto));
    }

    @Test
    void updateUserTest() {
        UserDto userDtoTest = UserTestDataUtil.createUserDto();
        User testUser = UserMapper.INSTANCE.mapUserDtoToUser(userDtoTest);
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(testUser));
        when(userRepository.save(testUser)).thenReturn(testUser);

        UserDto userDto = userService.updateUser(TEST_EMAIL, userDtoTest);

        assertThat(userDto, is(userDto));

        verify(userRepository, times(1)).findByEmail(TEST_EMAIL);
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void updateUserEntityNotFoundTest() {
        UserDto userDto = UserTestDataUtil.createUserDto();
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.updateUser(TEST_EMAIL, userDto));
    }

    @Test
    void deleteUserTest() {
        User user = UserTestDataUtil.createUser();
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(user));

        userService.deleteUser(user.getEmail());

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void deleteEntityNotFoundTest() {
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(TEST_EMAIL));
    }

    @Test
    void listUsersWithPageRequestTest() {
        UserDto userDto1 = UserTestDataUtil.createUserDto();

        UserDto userDto2 = UserTestDataUtil.createUserDto();
        userDto2.setId(2L);

        UserDto userDto3 = UserTestDataUtil.createUserDto();
        userDto3.setId(3L);

        List<User> users =
                Arrays.asList(
                        UserMapper.INSTANCE.mapUserDtoToUser(userDto1),
                        UserMapper.INSTANCE.mapUserDtoToUser(userDto2),
                        UserMapper.INSTANCE.mapUserDtoToUser(userDto3));

        Pageable pageable = PageRequest.of(0, 2);

        when(userRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(users.subList(0, 2), pageable, users.size()));

        Page<UserDto> pagedUsers = userService.listUsers(pageable);

        assertThat(pagedUsers.getSize(), is(2));
        assertThat(pagedUsers.getPageable(), is(pageable));
        assertThat(pagedUsers.getContent(), hasItems(userDto1, userDto2));

        verify(userRepository, times(1)).findAll(pageable);
    }

    @Test
    void listUsersWithEmptyPageRequest() {
        UserDto userDto1 = UserTestDataUtil.createUserDto();

        UserDto userDto2 = UserTestDataUtil.createUserDto();
        userDto2.setId(2L);

        UserDto userDto3 = UserTestDataUtil.createUserDto();
        userDto3.setId(3L);

        List<User> users =
                Arrays.asList(
                        UserMapper.INSTANCE.mapUserDtoToUser(userDto1),
                        UserMapper.INSTANCE.mapUserDtoToUser(userDto2),
                        UserMapper.INSTANCE.mapUserDtoToUser(userDto3));

        Pageable pageable = Pageable.unpaged();

        when(userRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(users, pageable, users.size()));

        Page<UserDto> resultPageableUsers = userService.listUsers(pageable);

        assertThat(resultPageableUsers.getContent(), hasItems(userDto1, userDto2, userDto3));

        verify(userRepository, times(1)).findAll(pageable);
    }

}
