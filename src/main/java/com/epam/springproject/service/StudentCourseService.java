package com.epam.springproject.service;

import com.epam.springproject.dto.StudentCourseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentCourseService {

    StudentCourseDto startCourse(StudentCourseDto studentCourseDto);

    StudentCourseDto finishCourse(StudentCourseDto studentCourseDto, long courseId);

    Page<StudentCourseDto> findByStudentId(Long studentId, Pageable pageable);
}
