package com.epam.springproject.controller;

import com.epam.springproject.api.CourseApi;
import com.epam.springproject.dto.CourseDto;
import com.epam.springproject.dto.group.OnCreate;
import com.epam.springproject.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CourseController implements CourseApi {

    private final CourseService courseService;

    @Override
    public List<CourseDto> getTeacherCourses(long teacherId) {
        log.info("CourseController getTeacherCourses with teacherId " + teacherId);
        return courseService.getTeacherCourses(teacherId);
    }

    @Override
    public List<CourseDto> getAllCourses() {
        log.info("CourseController getAllCourses method");
        return courseService.getAllCourses();
    }

    @Override
    public CourseDto createCourse(CourseDto courseDto) {
        log.info("CourseController createCourse with name " + courseDto.getName());
        return courseService.createCourse(courseDto);
    }

    @Override
    public CourseDto updateCourse(CourseDto courseDto, long courseId) {
        log.info("CourseController updateCourse with teacherId " + courseId);
        return courseService.updateCourse(courseDto, courseId);
    }

    @Override
    public ResponseEntity<Void> deleteCourse(long teacherId) {
        log.info("CourseController deleteCourse with teacherId " + teacherId);
        courseService.deleteCourse(teacherId);
        return ResponseEntity.noContent().build();
    }

}
