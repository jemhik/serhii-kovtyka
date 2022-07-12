package com.epam.springproject.repository.impl;

import com.epam.springproject.model.Course;
import com.epam.springproject.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CourseRepositoryImpl implements CourseRepository {

    private final List<Course> list = new ArrayList<>();

    @Override
    public List<Course> getTeacherCourses(long teacherId) {
        log.info("CourseRepository getTeacherCourses with teacherId " + teacherId);
        return list.stream()
                .filter(course -> course.getTeacherId() == teacherId)
                .toList();
    }

    @Override
    public List<Course> getAllCourses() {
        log.info("CourseRepository getAllCourses method");
        return new ArrayList<>(list);
    }

    @Override
    public Course createCourse(Course course) {
        log.info("CourseRepository createCourse with name " + course.getName());
        list.add(course);
        return course;
    }

    @Override
    public Course updateCourse(Course course, long courseId) {
        log.info("CourseRepository updateCourse with courseId " + courseId);
        boolean isDeleted = list.removeIf(c -> c.getCourseId() == courseId);
        if (isDeleted) {
            list.add(course);
        } else {
            throw new RuntimeException("Course is not found!");
        }
        return course;
    }

    @Override
    public void deleteCourse(long courseId) {
        log.info("CourseRepository deleteCourse with courseId " + courseId);
        list.removeIf(course -> course.getCourseId() == courseId);
    }
}
