package com.epam.springproject.service;

import com.epam.springproject.dto.StudentCourseDto;
import com.epam.springproject.mapper.StudentCourseMapper;
import com.epam.springproject.model.Course;
import com.epam.springproject.model.StudentCourse;
import com.epam.springproject.model.User;
import com.epam.springproject.repository.CourseRepository;
import com.epam.springproject.repository.StudentCourseRepository;
import com.epam.springproject.repository.UserRepository;
import com.epam.springproject.service.impl.StudentCourseServiceImpl;
import com.epam.springproject.test.util.CourseTestDataUtil;
import com.epam.springproject.test.util.StudentCourseDataUtil;
import com.epam.springproject.test.util.UserTestDataUtil;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentCourseServiceImplTest {
    @InjectMocks
    private StudentCourseServiceImpl studentCourseService;

    @Mock
    private StudentCourseRepository studentCourseRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private UserRepository userRepository;

    @Test
    void startCourseTest() {
        Course course = CourseTestDataUtil.createCourse();
        User student = UserTestDataUtil.createUser();
        StudentCourse studentCourse = StudentCourseDataUtil
                .createStudentCourse(StudentCourseDataUtil.COURSE_PROGRESS_STARTED);
        StudentCourseDto testStudentCourseDto = StudentCourseDataUtil
                .createStudentCourseDto(StudentCourseDataUtil.COURSE_PROGRESS_STARTED);

        when(courseRepository.getById(course.getCourseId())).thenReturn(course);
        when(userRepository.getById(student.getId())).thenReturn(student);
        when(studentCourseRepository.save(studentCourse)).thenReturn(studentCourse);

        StudentCourseDto studentCourseDto = studentCourseService.startCourse(testStudentCourseDto);

        assertThat(studentCourseDto, allOf(
                hasProperty("studentCourseId", equalTo(studentCourseDto.getStudentCourseId())),
                hasProperty("progress", equalTo(studentCourseDto.getProgress())),
                hasProperty("studentSolution", equalTo(studentCourseDto.getStudentSolution())),
                hasProperty("student", equalTo(studentCourseDto.getStudent())),
                hasProperty("course", equalTo(studentCourseDto.getCourse()))
        ));
    }

    @Test
    void finishCourseTest() {
        StudentCourseDto testStudentCourseDto = StudentCourseDataUtil
                .createStudentCourseDto(StudentCourseDataUtil.COURSE_PROGRESS_SUBMITTED);
        StudentCourse studentCourse = StudentCourseDataUtil
                .createStudentCourse(StudentCourseDataUtil.COURSE_PROGRESS_SUBMITTED);

        when(studentCourseRepository.getById(StudentCourseDataUtil.STUDENT_COURSE_ID)).thenReturn(studentCourse);

        StudentCourseDto studentCourseDto = studentCourseService
                .finishCourse(testStudentCourseDto, StudentCourseDataUtil.STUDENT_COURSE_ID);

        verify(studentCourseRepository, times(1)).finishCourse(testStudentCourseDto.getStudentSolution(),
                testStudentCourseDto.getProgress(), testStudentCourseDto.getStudentCourseId());

        assertThat(studentCourseDto, allOf(
                hasProperty("studentCourseId", equalTo(studentCourseDto.getStudentCourseId())),
                hasProperty("progress", equalTo(studentCourseDto.getProgress())),
                hasProperty("studentSolution", equalTo(studentCourseDto.getStudentSolution())),
                hasProperty("student", equalTo(studentCourseDto.getStudent())),
                hasProperty("course", equalTo(studentCourseDto.getCourse()))
        ));
    }

    @Test
    void findByStudentIdTest() {
        User student = UserTestDataUtil.createUser();

        StudentCourseDto studentCourseDto1 = StudentCourseDataUtil
                .createStudentCourseDto(StudentCourseDataUtil.COURSE_PROGRESS_STARTED);

        StudentCourseDto studentCourseDto2 = StudentCourseDataUtil
                .createStudentCourseDto(StudentCourseDataUtil.COURSE_PROGRESS_STARTED);
        studentCourseDto2.setStudentCourseId(2L);

        StudentCourseDto studentCourseDto3 = StudentCourseDataUtil
                .createStudentCourseDto(StudentCourseDataUtil.COURSE_PROGRESS_STARTED);
        studentCourseDto3.setStudentCourseId(3L);

        List<StudentCourse> studentCourses =
                Arrays.asList(
                        StudentCourseMapper.INSTANCE.mapStudentCourseDtoToStudentCourse(studentCourseDto1),
                        StudentCourseMapper.INSTANCE.mapStudentCourseDtoToStudentCourse(studentCourseDto2),
                        StudentCourseMapper.INSTANCE.mapStudentCourseDtoToStudentCourse(studentCourseDto3));

        Pageable pageable = PageRequest.of(0, 2);

        when(studentCourseRepository.findByStudentId(student.getId(), pageable))
                .thenReturn(new PageImpl<>(studentCourses.subList(0, 2), pageable, studentCourses.size()));

        Page<StudentCourseDto> pagedStudentCourses = studentCourseService.findByStudentId(student.getId(), pageable);

        assertThat(pagedStudentCourses.getSize(), is(2));
        assertThat(pagedStudentCourses.getPageable(), is(pageable));
        assertThat(pagedStudentCourses.getContent(), hasItems(studentCourseDto1, studentCourseDto2));

        verify(studentCourseRepository, times(1)).findByStudentId(student.getId(), pageable);
    }

}
