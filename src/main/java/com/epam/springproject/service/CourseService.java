package com.epam.springproject.service;

import com.epam.springproject.dto.CourseDto;

import java.util.List;

public interface CourseService {

    List<CourseDto> getTeacherCourses(long teacherId);

    List<CourseDto> getAllCourses();

    CourseDto createCourse(CourseDto course);

    CourseDto updateCourse(CourseDto course, long courseId);

    void deleteCourse(long courseId);

}
