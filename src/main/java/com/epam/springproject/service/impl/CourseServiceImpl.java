package com.epam.springproject.service.impl;

import com.epam.springproject.dto.CourseDto;
import com.epam.springproject.mapper.CourseMapper;
import com.epam.springproject.repository.CourseRepository;
import com.epam.springproject.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public List<CourseDto> getTeacherCourses(long teacherId) {
        log.info("CourseService getTeacherCourses with teacher id " + teacherId);
        return courseRepository.getTeacherCourses(teacherId)
                .stream()
                .map(CourseMapper.INSTANCE::mapCourseToCourseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> getAllCourses() {
        log.info("CourseService getAllCourses method");
        return courseRepository.getAllCourses()
                .stream()
                .map(CourseMapper.INSTANCE::mapCourseToCourseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CourseDto createCourse(CourseDto course) {
        log.info("CourseService createCourse with name " + course.getName());
        return CourseMapper.INSTANCE.mapCourseToCourseDto(
                courseRepository.createCourse(
                        CourseMapper.INSTANCE.mapCourseDtoToCourse(course)));
    }

    @Override
    public CourseDto updateCourse(CourseDto course, long courseId) {
        log.info("CourseService updateCourse with name " + course.getName());
        return CourseMapper.INSTANCE.mapCourseToCourseDto(
                courseRepository.updateCourse(
                        CourseMapper.INSTANCE.mapCourseDtoToCourse(course), courseId));
    }

    @Override
    public void deleteCourse(long courseId) {
        log.info("CourseService deleteCourse with courseId " + courseId);
        courseRepository.deleteCourse(courseId);
    }

//    private CourseDto mapCourseToCourseDto(Course course) {
//        return CourseDto.builder()
//                .name(course.getName())
//                .description(course.getDescription())
//                .duration(course.getDuration())
//                .materials(course.getMaterials())
//                .task(course.getTask())
//                .build();
//    }
//
//    private Course mapCourseDtoToCourse(CourseDto courseDto) {
//        return Course.builder()
//                .name(courseDto.getName())
//                .description(courseDto.getDescription())
//                .duration(courseDto.getDuration())
//                .materials(courseDto.getMaterials())
//                .task(courseDto.getTask())
//                .build();
//    }

}
