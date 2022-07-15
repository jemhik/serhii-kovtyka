package com.epam.springproject.repository;

import com.epam.springproject.model.Course;

import java.util.List;

public interface CourseRepository {

    List<Course> getTeacherCourses(long teacherId);

    List<Course> getAllCourses();

    Course createCourse(Course course);

    Course updateCourse(Course course, long courseId);

    void deleteCourse(long courseId);

}
