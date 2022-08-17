package com.epam.springproject.controller;

import com.epam.springproject.api.StudentCourseApi;
import com.epam.springproject.dto.JournalDto;
import com.epam.springproject.dto.StudentCourseDto;
import com.epam.springproject.service.RateStudentService;
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
    private final RateStudentService rateStudentService;

    @Override
    public Page<StudentCourseDto> findByStudentId(Pageable pageable, long studentId) {
        log.info("StudentCourseController find courses by student id " + studentId);
        return studentCourseService.findByStudentId(studentId, pageable);
    }

    @Override
    public StudentCourseDto startCourse(StudentCourseDto studentCourseDto) {
        log.info("StudentCourseController start course with courseId {}, studentId {}",
                studentCourseDto.getCourse().getCourseId(), studentCourseDto.getStudent().getId());
        return studentCourseService.startCourse(studentCourseDto);
    }

    @Override
    public StudentCourseDto finishCourse(StudentCourseDto studentCourseDto, long courseId) {
        log.info("StudentCourseController finish course with studentCourseId " + courseId);
        return studentCourseService.finishCourse(studentCourseDto, courseId);
    }

    @Override
    public Page<StudentCourseDto> findFinishedCourses(Pageable pageable) {
        log.info("StudentCourseController find finished courses method");
        return rateStudentService.findFinishedCourses(pageable);
    }

    @Override
    public JournalDto rateStudent(JournalDto journalDto) {
        log.info("StudentCourseController rate student course with studentCourseId " +
                journalDto.getStudentCourse().getStudentCourseId());
        return rateStudentService.rateStudent(journalDto);
    }
}
