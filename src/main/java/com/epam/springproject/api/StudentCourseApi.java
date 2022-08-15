package com.epam.springproject.api;

import com.epam.springproject.dto.CourseDto;
import com.epam.springproject.dto.JournalDto;
import com.epam.springproject.dto.StudentCourseDto;
import com.epam.springproject.dto.group.OnCreate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Student Course management API")
@RequestMapping("/api/v1/studentCourses")
public interface StudentCourseApi {

    @ApiOperation("Get student's courses")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{studentId}")
    Page<StudentCourseDto> findByStudentId(Pageable pageable, @PathVariable long studentId);

    @ApiOperation("Start course")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/")
    StudentCourseDto startCourse(@RequestBody @Validated(OnCreate.class) StudentCourseDto studentCourseDto);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", paramType = "path", required = true, value = "Course id")
    })
    @ApiOperation("Finish course")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{courseId}")
    StudentCourseDto finishCourse(@RequestBody @Valid StudentCourseDto studentCourseDto, @PathVariable long courseId);

    @ApiOperation("Get finished courses")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/finished/")
    Page<StudentCourseDto> findFinishedCourses(Pageable pageable);

    @ApiOperation("Rate student")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/rate/")
    JournalDto rateStudent(@RequestBody JournalDto journalDto);
}
