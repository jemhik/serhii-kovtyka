package com.epam.springproject.test.util;

import com.epam.springproject.dto.StudentCourseDto;
import com.epam.springproject.model.Course;
import com.epam.springproject.model.StudentCourse;
import com.epam.springproject.model.User;

public class StudentCourseDataUtil {

    public static final Long STUDENT_COURSE_ID = 1L;
    public static final String COURSE_PROGRESS_STARTED = "started";
    public static final String COURSE_PROGRESS_SUBMITTED = "submitted";
    public static final String STUDENT_SOLUTION = "test student solution";

    public static StudentCourse createStudentCourse(String progress) {
        return StudentCourse.builder()
                .studentCourseId(STUDENT_COURSE_ID)
                .progress(progress)
                .studentSolution(STUDENT_SOLUTION)
                .student(UserTestDataUtil.createUser())
                .course(CourseTestDataUtil.createCourse())
                .build();
    }

    public static StudentCourseDto createStudentCourseDto(String progress) {
        return StudentCourseDto.builder()
                .studentCourseId(STUDENT_COURSE_ID)
                .progress(progress)
                .studentSolution(STUDENT_SOLUTION)
                .course(Course.builder().courseId(1L).build())
                .student(User.builder().id(1L).build())
                .build();
    }

}
