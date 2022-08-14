package com.epam.springproject.service;

import com.epam.springproject.dto.CourseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {

    Page<CourseDto> getTeacherCourses(Pageable pageable, long teacherId);

    Page<CourseDto> listCourses(Pageable pageable);

    CourseDto createCourse(CourseDto course);

    CourseDto updateCourse(CourseDto course);

    void deleteCourse(long courseId);

}
