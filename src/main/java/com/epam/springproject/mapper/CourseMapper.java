package com.epam.springproject.mapper;

import com.epam.springproject.dto.CourseDto;
import com.epam.springproject.model.Course;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CourseMapper {
    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    CourseDto mapCourseToCourseDto(Course course);

    Course mapCourseDtoToCourse(CourseDto courseDto);
}
