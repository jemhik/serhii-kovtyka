package com.epam.springproject.service;

import com.epam.springproject.dto.CourseDto;
import com.epam.springproject.dto.UserDto;
import com.epam.springproject.exception.EntityNotFoundException;
import com.epam.springproject.mapper.CourseMapper;
import com.epam.springproject.mapper.UserMapper;
import com.epam.springproject.model.Course;
import com.epam.springproject.model.User;
import com.epam.springproject.repository.CourseRepository;
import com.epam.springproject.repository.UserRepository;
import com.epam.springproject.service.impl.CourseServiceImpl;
import com.epam.springproject.test.util.CourseTestDataUtil;
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

import static com.epam.springproject.test.util.UserTestDataUtil.TEST_EMAIL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceImplTest {
    @InjectMocks
    private CourseServiceImpl courseService;

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private UserRepository userRepository;

    @Test
    void getTeacherCoursesTest() {
        User teacher = UserTestDataUtil.createTeacher();

        CourseDto courseDto1  = CourseTestDataUtil.createCourseDto();

        CourseDto courseDto2  = CourseTestDataUtil.createCourseDto();
        courseDto2.setCourseId(2L);

        CourseDto courseDto3  = CourseTestDataUtil.createCourseDto();
        courseDto3.setCourseId(3L);

        List<Course> courses =
                Arrays.asList(
                        CourseMapper.INSTANCE.mapCourseDtoToCourse(courseDto1),
                        CourseMapper.INSTANCE.mapCourseDtoToCourse(courseDto2),
                        CourseMapper.INSTANCE.mapCourseDtoToCourse(courseDto3));

        Pageable pageable = PageRequest.of(0, 2);

        when(courseRepository.findByTeacherId(teacher.getId(), pageable))
                .thenReturn(new PageImpl<>(courses.subList(0, 2), pageable, courses.size()));

        Page<CourseDto> coursesWithTeacherId = courseService.getCoursesByTeacherId(pageable, teacher.getId());

        assertThat(coursesWithTeacherId.getSize(), is(2));
        assertThat(coursesWithTeacherId.getPageable(), is(pageable));
        assertThat(coursesWithTeacherId.getContent(), hasItems(courseDto1, courseDto2));

        verify(courseRepository, times(1)).findByTeacherId(teacher.getId(), pageable);
    }

    @Test
    void listCoursesTest() {
        CourseDto courseDto1  = CourseTestDataUtil.createCourseDto();

        CourseDto courseDto2  = CourseTestDataUtil.createCourseDto();
        courseDto2.setCourseId(2L);

        CourseDto courseDto3  = CourseTestDataUtil.createCourseDto();
        courseDto3.setCourseId(3L);

        List<Course> courses =
                Arrays.asList(
                        CourseMapper.INSTANCE.mapCourseDtoToCourse(courseDto1),
                        CourseMapper.INSTANCE.mapCourseDtoToCourse(courseDto2),
                        CourseMapper.INSTANCE.mapCourseDtoToCourse(courseDto3));

        Pageable pageable = PageRequest.of(0, 2);

        when(courseRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(courses.subList(0, 2), pageable, courses.size()));

        Page<CourseDto> pagedCourses = courseService.listCourses(pageable);

        assertThat(pagedCourses.getSize(), is(2));
        assertThat(pagedCourses.getPageable(), is(pageable));
        assertThat(pagedCourses.getContent(), hasItems(courseDto1, courseDto2));

        verify(courseRepository, times(1)).findAll(pageable);
    }

    @Test
    void createCourseTest() {
        Course course = CourseTestDataUtil.createCourse();
        CourseDto testCourseDto = CourseTestDataUtil.createCourseDto();
        when(userRepository.findById(course.getTeacher().getId())).
                thenReturn(Optional.of(UserTestDataUtil.createTeacher()));
        when(courseRepository.save(course)).thenReturn(course);

        CourseDto courseDto = courseService.createCourse(testCourseDto);

        assertThat(courseDto, allOf(
                hasProperty("courseId", equalTo(course.getCourseId())),
                hasProperty("name", equalTo(course.getName())),
                hasProperty("description", equalTo(course.getDescription())),
                hasProperty("materials", equalTo(course.getMaterials())),
                hasProperty("task", equalTo(course.getTask())),
                hasProperty("duration", equalTo(course.getDuration())),
                hasProperty("teacher", equalTo(course.getTeacher()))
        ));
    }

    @Test
    void createCourseEntityNotFoundTest() {
        CourseDto testCourseDto = CourseTestDataUtil.createCourseDto();
        when(userRepository.findById(testCourseDto.getTeacher().getId())).
                thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> courseService.createCourse(testCourseDto));
    }

    @Test
    void updateCourseTest() {
        Course course = CourseTestDataUtil.createCourse();
        CourseDto testCourseDto = CourseTestDataUtil.createCourseDto();
        when(courseRepository.getById(course.getCourseId())).thenReturn(course);
        when(courseRepository.save(course)).thenReturn(course);

        CourseDto courseDto = courseService.updateCourse(testCourseDto);

        assertThat(courseDto, is(testCourseDto));

        verify(courseRepository, times(1)).getById(course.getCourseId());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void deleteCourseTest() {
        Course course = CourseTestDataUtil.createCourse();
        courseService.deleteCourse(course.getCourseId());
        verify(courseRepository, times(1)).deleteById(course.getCourseId());
    }

}
