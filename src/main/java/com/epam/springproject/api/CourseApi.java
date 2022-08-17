package com.epam.springproject.api;

import com.epam.springproject.dto.CourseDto;
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

@Api(tags = "Course management API")
@RequestMapping("/api/v1/courses")
public interface CourseApi {

    @ApiOperation("Get teacher courses")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{teacherId}")
    Page<CourseDto> getTeacherCourses(Pageable pageable, @PathVariable long teacherId);

    @ApiOperation("Get all courses")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    Page<CourseDto> listCourses(@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNumber,
                                @RequestParam("sortType") String sortType);

    @ApiOperation("Create course")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/")
    CourseDto createCourse(@RequestBody @Validated(OnCreate.class) CourseDto courseDto);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", paramType = "path", required = true, value = "Course id")
    })
    @ApiOperation("Update course")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{courseId}")
    CourseDto updateCourse(@RequestBody @Valid CourseDto courseDto, @PathVariable long courseId);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId", paramType = "path", required = true, value = "Course id")
    })
    @ApiOperation("Delete course")
    @DeleteMapping(value = "/{courseId}")
    ResponseEntity<Void> deleteCourse(@PathVariable long courseId);
}
