package com.epam.springproject.service.impl;

import com.epam.springproject.dto.StudentCourseDto;
import com.epam.springproject.exception.EntityNotFoundException;
import com.epam.springproject.mapper.StudentCourseMapper;
import com.epam.springproject.model.StudentCourse;
import com.epam.springproject.repository.CourseRepository;
import com.epam.springproject.repository.StudentCourseRepository;
import com.epam.springproject.repository.UserRepository;
import com.epam.springproject.service.StudentCourseService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentCourseServiceImpl implements StudentCourseService {

    private final StudentCourseRepository studentCourseRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public StudentCourseDto startCourse(StudentCourseDto studentCourseDto) {
        log.info("StudentCourseService startCourse with courseId {}, studentId {}",
                studentCourseDto.getCourse().getCourseId(), studentCourseDto.getStudent().getId());

        studentCourseDto.setCourse(courseRepository.getById(studentCourseDto.getCourse().getCourseId()));
        studentCourseDto.setStudent(userRepository.getById(studentCourseDto.getStudent().getId()));

        return StudentCourseMapper.INSTANCE.mapStudentCourseToStudentCourseDto(
                studentCourseRepository.save(
                        StudentCourseMapper.INSTANCE.
                                mapStudentCourseDtoToStudentCourse(studentCourseDto))
        );
    }

    @Override
    @Transactional
    public StudentCourseDto finishCourse(StudentCourseDto studentCourseDto, long courseId) {
        log.info("StudentCourseService finishCourse with courseId {}, studentId {}",
                studentCourseDto.getCourse().getCourseId(), studentCourseDto.getStudent().getId());

        studentCourseRepository.
                finishCourse(studentCourseDto.getStudentSolution(), "submitted", courseId);
        return StudentCourseMapper.INSTANCE.mapStudentCourseToStudentCourseDto(
                studentCourseRepository
                        .getById(courseId)
        );
    }

    @Override
    public Page<StudentCourseDto> findByStudentId(Long studentId, Pageable pageable) {
        log.info("StudentCourseService findByStudentId with studentId" + studentId);
        return studentCourseRepository.findByStudentId(studentId, pageable)
                .map(StudentCourseMapper.INSTANCE::mapStudentCourseToStudentCourseDto);
    }

}
