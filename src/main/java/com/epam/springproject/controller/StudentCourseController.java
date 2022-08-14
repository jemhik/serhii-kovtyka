package com.epam.springproject.controller;

import com.epam.springproject.api.StudentCourseApi;
import com.epam.springproject.dto.StudentCourseDto;
import com.epam.springproject.model.Course;
import com.epam.springproject.model.StudentCourse;
import com.epam.springproject.service.StudentCourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StudentCourseController implements StudentCourseApi {

    private final StudentCourseService studentCourseService;

    @Override
    public Page<StudentCourseDto> findByStudentId(Pageable pageable, long studentId) {
        return studentCourseService.findByStudentId(studentId, pageable);
    }

    @Override
    public StudentCourseDto startCourse(StudentCourseDto studentCourseDto) {
        return studentCourseService.startCourse(studentCourseDto);
    }

    @Override
    public StudentCourseDto finishCourse(StudentCourseDto studentCourseDto, long courseId) {
        return studentCourseService.finishCourse(studentCourseDto, courseId);
    }
}
