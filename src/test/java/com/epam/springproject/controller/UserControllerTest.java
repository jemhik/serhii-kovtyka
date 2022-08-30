package com.epam.springproject.controller;

import com.epam.springproject.dto.UserDto;
import com.epam.springproject.exception.UserAlreadyExistsException;
import com.epam.springproject.model.enums.ErrorType;
import com.epam.springproject.service.UserService;
import com.epam.springproject.test.config.TestConfig;
import com.epam.springproject.test.util.UserTestDataUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static com.epam.springproject.test.util.UserTestDataUtil.FIRST_NAME;
import static com.epam.springproject.test.util.UserTestDataUtil.TEST_EMAIL;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@Import(TestConfig.class)
public class UserControllerTest {
    public static final String URL = "/api/v1/users/";

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getUserTest() throws Exception {
        UserDto userDto = UserTestDataUtil.createUserDto();

        when(userService.getUser(TEST_EMAIL)).thenReturn(userDto);

        mockMvc.perform(get(URL + TEST_EMAIL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(TEST_EMAIL));
    }

    @Test
    void createUserTest() throws Exception {
        UserDto userDto = UserTestDataUtil.createUserDto();
        when(userService.createUser(userDto)).thenReturn(userDto);

        mockMvc.perform(post(URL)
                        .content(objectMapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(TEST_EMAIL));
    }

    @Test
    void updateUserTest() throws Exception {
        UserDto userDto = UserTestDataUtil.createUserDto();
        userDto.setEmail(null);
        userDto.setPassword(null);

        when(userService.updateUser(TEST_EMAIL, userDto)).thenReturn(userDto);

        mockMvc.perform(put(URL + TEST_EMAIL)
                        .content(objectMapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value(FIRST_NAME));
    }

    @Test
    void getAllUsersTest() throws Exception {

        UserDto userDto1 = UserTestDataUtil.createUserDto();

        UserDto userDto2 = UserTestDataUtil.createUserDto();
        userDto2.setId(2L);

        UserDto userDto3 = UserTestDataUtil.createUserDto();
        userDto3.setId(3L);

        List<UserDto> users = Arrays.asList(userDto1, userDto2, userDto3);
        Pageable pageable = PageRequest.of(1, 1, Sort.by(Sort.DEFAULT_DIRECTION, "id"));

        when(userService.listUsers(pageable)).thenReturn(new PageImpl<>(users.subList(0, 2), pageable, users.size()));

        mockMvc.perform(get(URL + "?pageSize=1&pageNumber=1&sortType=id"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].email").value(TEST_EMAIL));
    }

    @Test
    void deleteUserTest() throws Exception {
        mockMvc.perform(delete(URL + TEST_EMAIL))
                .andExpect(status().isNoContent());

        verify(userService).deleteUser(TEST_EMAIL);
    }

    @Test
    void updateNotValidUserTest() throws Exception {
        UserDto userDto = UserTestDataUtil.createUserDto();
        userDto.setFirstName("");
        userDto.setLastName("");
        userDto.setEmail("a");
        userDto.setPassword("");

        when(userService.updateUser(eq(userDto.getEmail()), eq(userDto))).thenReturn(userDto);

        mockMvc.perform(put(URL + userDto.getEmail())
                        .content(objectMapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUserAlreadyExist() throws Exception {
        UserDto userDto = UserTestDataUtil.createUserDto();
        when(userService.createUser(userDto))
                .thenThrow(new UserAlreadyExistsException("User already exists!"));

        mockMvc.perform(post(URL)
                        .content(objectMapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("User already exists!"))
                .andExpect(jsonPath("$.errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()));
    }

    @Test
    void getUserInternalServerError() throws Exception {
        when(userService.getUser(TEST_EMAIL))
                .thenThrow(new NullPointerException());
        mockMvc.perform(get(URL + TEST_EMAIL))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

}
