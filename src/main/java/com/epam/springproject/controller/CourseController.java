package com.epam.springproject.controller;

import com.epam.springproject.dto.CourseDto;
import com.epam.springproject.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/training-center/courses/")
@RequiredArgsConstructor
@Slf4j
public class CourseController {

    private final CourseService courseService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{teacherId}")
    public List<CourseDto> getTeacherCourses(@PathVariable long teacherId) {
        log.info("CourseController getTeacherCourses with teacherId " + teacherId);
        return courseService.getTeacherCourses(teacherId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/")
    public List<CourseDto> getAllCourses() {
        log.info("CourseController getAllCourses method");
        return courseService.getAllCourses();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/")
    public CourseDto createCourse(@RequestBody CourseDto courseDto) {
        log.info("CourseController createCourse with name " + courseDto.getName());
        return courseService.createCourse(courseDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{courseId}")
    public CourseDto updateCourse(@RequestBody CourseDto courseDto, @PathVariable long courseId) {
        log.info("CourseController updateCourse with teacherId " + courseId);
        return courseService.updateCourse(courseDto, courseId);
    }

    @DeleteMapping(value = "/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable long teacherId) {
        log.info("CourseController deleteCourse with teacherId " + teacherId);
        courseService.deleteCourse(teacherId);
        return ResponseEntity.noContent().build();
    }

}
