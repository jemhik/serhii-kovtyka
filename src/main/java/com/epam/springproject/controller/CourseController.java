package com.epam.springproject.controller;

import com.epam.springproject.api.CourseApi;
import com.epam.springproject.dto.CourseDto;
import com.epam.springproject.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CourseController implements CourseApi {

    private final CourseService courseService;

    @Override
    public Page<CourseDto> getTeacherCourses(Pageable pageable, long teacherId) {
        log.info("CourseController getTeacherCourses with teacherId " + teacherId);
        return courseService.getCoursesByTeacherId(pageable, teacherId);
    }

    @Override
    public Page<CourseDto> listCourses(int pageSize, int pageNumber, String sortType) {
        log.info("CourseController getAllCourses method");
        return courseService.listCourses(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.DEFAULT_DIRECTION, sortType)));
    }

    @Override
    public CourseDto createCourse(CourseDto courseDto) {
        log.info("CourseController createCourse with name " + courseDto.getName());
        return courseService.createCourse(courseDto);
    }

    @Override
    public CourseDto updateCourse(CourseDto courseDto, long courseId) {
        log.info("CourseController updateCourse with courseId " + courseId);
        courseDto.setCourseId(courseId);
        return courseService.updateCourse(courseDto);
    }

    @Override
    public ResponseEntity<Void> deleteCourse(long courseId) {
        log.info("CourseController deleteCourse with courseId " + courseId);
        courseService.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }

}
