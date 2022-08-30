package com.epam.springproject.controller;

import com.epam.springproject.dto.CourseDto;
import com.epam.springproject.model.User;
import com.epam.springproject.service.CourseService;
import com.epam.springproject.test.config.TestConfig;
import com.epam.springproject.test.util.CourseTestDataUtil;
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

import static com.epam.springproject.test.util.UserTestDataUtil.ID;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CourseController.class)
@Import(TestConfig.class)
public class CourseControllerTest {

    public static final String URL = "/api/v1/courses/";

    @MockBean
    private CourseService courseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getCoursesByTeacherId() throws Exception {
        User teacher = UserTestDataUtil.createTeacher();

        CourseDto courseDto1 = CourseTestDataUtil.createCourseDto();
        courseDto1.setTeacher(teacher);

        CourseDto courseDto2 = CourseTestDataUtil.createCourseDto();
        courseDto2.setTeacher(teacher);
        courseDto2.setCourseId(2L);

        CourseDto courseDto3 = CourseTestDataUtil.createCourseDto();
        courseDto3.setTeacher(teacher);
        courseDto3.setCourseId(3L);

        List<CourseDto> courses = Arrays.asList(courseDto1, courseDto2, courseDto3);
        Pageable pageable = PageRequest.of(0, 2);
        when(courseService.getCoursesByTeacherId(anyObject(), eq(ID)))
                .thenReturn(new PageImpl<>(courses.subList(0, 2), pageable, courses.size()));

        mockMvc.perform(get(URL + ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].name").value(CourseTestDataUtil.NAME));
    }

    @Test
    void createCourseTest() throws Exception {
        CourseDto courseDto = CourseTestDataUtil.createCourseDto();
        when(courseService.createCourse(courseDto)).thenReturn(courseDto);

        mockMvc.perform(post(URL)
                        .content(objectMapper.writeValueAsString(courseDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(CourseTestDataUtil.NAME));
    }

    @Test
    void updateCourseTest() throws Exception {
        CourseDto courseDto = CourseTestDataUtil.createCourseDto();
        courseDto.setTeacher(null);

        when(courseService.updateCourse(courseDto)).thenReturn(courseDto);

        mockMvc.perform(put(URL + courseDto.getCourseId())
                        .content(objectMapper.writeValueAsString(courseDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(CourseTestDataUtil.NAME));
    }

    @Test
    void getAllCoursesTest() throws Exception {
        CourseDto courseDto1 = CourseTestDataUtil.createCourseDto();

        CourseDto courseDto2 = CourseTestDataUtil.createCourseDto();
        courseDto2.setCourseId(2L);

        CourseDto courseDto3 = CourseTestDataUtil.createCourseDto();
        courseDto3.setCourseId(3L);

        List<CourseDto> courses = Arrays.asList(courseDto1, courseDto2, courseDto3);
        Pageable pageable = PageRequest.of(1, 1, Sort.by(Sort.DEFAULT_DIRECTION, "courseId"));

        when(courseService.listCourses(pageable))
                .thenReturn(new PageImpl<>(courses.subList(0, 2), pageable, courses.size()));

        mockMvc.perform(get(URL + "?pageSize=1&pageNumber=1&sortType=courseId"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].name").value(CourseTestDataUtil.NAME));
    }

    @Test
    void deleteCourseTest() throws Exception {
        mockMvc.perform(delete(URL + CourseTestDataUtil.COURSE_ID))
                .andExpect(status().isNoContent());

        verify(courseService).deleteCourse(CourseTestDataUtil.COURSE_ID);
    }
}
