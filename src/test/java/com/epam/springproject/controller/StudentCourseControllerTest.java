package com.epam.springproject.controller;

import com.epam.springproject.dto.JournalDto;
import com.epam.springproject.dto.StudentCourseDto;
import com.epam.springproject.model.StudentCourse;
import com.epam.springproject.model.User;
import com.epam.springproject.service.RateStudentService;
import com.epam.springproject.service.StudentCourseService;
import com.epam.springproject.test.config.TestConfig;
import com.epam.springproject.test.util.StudentCourseDataUtil;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StudentCourseController.class)
@Import(TestConfig.class)
public class StudentCourseControllerTest {

    public static final String URL = "/api/v1/studentCourses/";

    @MockBean
    private StudentCourseService studentCourseService;
    @MockBean
    private RateStudentService rateStudentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findByStudentIdTest() throws Exception {
        User student = UserTestDataUtil.createUser();

        StudentCourseDto courseDto1 = StudentCourseDataUtil
                .createStudentCourseDto(StudentCourseDataUtil.COURSE_PROGRESS_STARTED);

        StudentCourseDto courseDto2 = StudentCourseDataUtil
                .createStudentCourseDto(StudentCourseDataUtil.COURSE_PROGRESS_STARTED);
        courseDto2.setStudentCourseId(2L);

        StudentCourseDto courseDto3 = StudentCourseDataUtil
                .createStudentCourseDto(StudentCourseDataUtil.COURSE_PROGRESS_STARTED);
        courseDto3.setStudentCourseId(3L);

        List<StudentCourseDto> courses = Arrays.asList(courseDto1, courseDto2, courseDto3);
        Pageable pageable = PageRequest.of(0, 2);

        when(studentCourseService.findByStudentId(eq(student.getId()), anyObject()))
                .thenReturn(new PageImpl<>(courses.subList(0, 2), pageable, courses.size()));

        mockMvc.perform(get(URL + student.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].studentCourseId")
                        .value(StudentCourseDataUtil.STUDENT_COURSE_ID));
    }

    @Test
    void startCourseTest() throws Exception {
        StudentCourseDto courseDto = StudentCourseDataUtil
                .createStudentCourseDto(StudentCourseDataUtil.COURSE_PROGRESS_STARTED);

        when(studentCourseService.startCourse(courseDto)).thenReturn(courseDto);
        mockMvc.perform(post(URL)
                        .content(objectMapper.writeValueAsString(courseDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.studentCourseId").value(StudentCourseDataUtil.STUDENT_COURSE_ID));
    }

    @Test
    void finishCourseTest() throws Exception {
        StudentCourseDto courseDto = StudentCourseDataUtil
                .createStudentCourseDto(StudentCourseDataUtil.COURSE_PROGRESS_SUBMITTED);

        when(studentCourseService.finishCourse(courseDto, courseDto.getStudentCourseId())).thenReturn(courseDto);

        mockMvc.perform(put(URL + courseDto.getStudentCourseId())
                        .content(objectMapper.writeValueAsString(courseDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.studentCourseId").value(StudentCourseDataUtil.STUDENT_COURSE_ID));
    }

    @Test
    void findFinishedCoursesTest() throws Exception {

        StudentCourseDto courseDto1 = StudentCourseDataUtil
                .createStudentCourseDto(StudentCourseDataUtil.COURSE_PROGRESS_SUBMITTED);

        StudentCourseDto courseDto2 = StudentCourseDataUtil
                .createStudentCourseDto(StudentCourseDataUtil.COURSE_PROGRESS_SUBMITTED);
        courseDto2.setStudentCourseId(2L);

        StudentCourseDto courseDto3 = StudentCourseDataUtil
                .createStudentCourseDto(StudentCourseDataUtil.COURSE_PROGRESS_SUBMITTED);
        courseDto3.setStudentCourseId(3L);

        List<StudentCourseDto> courses = Arrays.asList(courseDto1, courseDto2, courseDto3);
        Pageable pageable = PageRequest.of(0, 2);

        when(rateStudentService.findFinishedCourses(anyObject()))
                .thenReturn(new PageImpl<>(courses.subList(0, 2), pageable, courses.size()));

        mockMvc.perform(get(URL + "finished/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].studentCourseId")
                        .value(StudentCourseDataUtil.STUDENT_COURSE_ID));
    }

    @Test
    void rateStudentTest() throws Exception {
        JournalDto testJournalDto = JournalDto.builder()
                .assessmentId(1L)
                .assessment(10L)
                .studentCourse(StudentCourse.builder().studentCourseId(1L).build())
                .build();

        when(rateStudentService.rateStudent(testJournalDto)).thenReturn(testJournalDto);
        mockMvc.perform(post(URL + "rate/")
                        .content(objectMapper.writeValueAsString(testJournalDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.assessmentId").value(1L));
    }

}
