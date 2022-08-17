package com.epam.springproject.service.impl;

import com.epam.springproject.dto.CourseDto;
import com.epam.springproject.exception.EntityNotFoundException;
import com.epam.springproject.mapper.CourseMapper;
import com.epam.springproject.model.Course;
import com.epam.springproject.repository.CourseRepository;
import com.epam.springproject.repository.UserRepository;
import com.epam.springproject.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public Page<CourseDto> getTeacherCourses(Pageable pageable, long teacherId) {
        log.info("CourseService getTeacherCourses with teacher id " + teacherId);
        return courseRepository.findByTeacherId(teacherId, pageable)
                .map(CourseMapper.INSTANCE::mapCourseToCourseDto);

    }

    @Override
    public Page<CourseDto> listCourses(Pageable pageable) {
        log.info("CourseService getAllCourses method");
        return courseRepository.findAll(pageable)
                .map(CourseMapper.INSTANCE::mapCourseToCourseDto);
    }

    @Override
    public CourseDto createCourse(CourseDto course) {
        log.info("CourseService createCourse with name " + course.getName());

        course.setTeacher(userRepository.findById(course.getTeacher().getId()).orElseThrow(EntityNotFoundException::new));

        return CourseMapper.INSTANCE.mapCourseToCourseDto(
                courseRepository.save(
                        CourseMapper.INSTANCE.mapCourseDtoToCourse(course)));
    }

    @Override
    public CourseDto updateCourse(CourseDto course) {
        log.info("CourseService updateCourse with name " + course.getName());

        Course oldCourse = courseRepository.getById(course.getCourseId());
        course.setTeacher(oldCourse.getTeacher());
        course.setCourseId(course.getCourseId());

        return CourseMapper.INSTANCE.mapCourseToCourseDto(
                courseRepository.save(CourseMapper.INSTANCE.mapCourseDtoToCourse(course)));
    }

    @Override
    public void deleteCourse(long courseId) {
        log.info("CourseService deleteCourse with courseId " + courseId);
        courseRepository.deleteById(courseId);
    }
}
