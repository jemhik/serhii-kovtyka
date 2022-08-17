package com.epam.springproject.mapper;

import com.epam.springproject.dto.StudentCourseDto;
import com.epam.springproject.model.StudentCourse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentCourseMapper {

    StudentCourseMapper INSTANCE = Mappers.getMapper(StudentCourseMapper.class);

    StudentCourseDto mapStudentCourseToStudentCourseDto(StudentCourse studentCourse);

    StudentCourse mapStudentCourseDtoToStudentCourse(StudentCourseDto studentCourseDto);
}
