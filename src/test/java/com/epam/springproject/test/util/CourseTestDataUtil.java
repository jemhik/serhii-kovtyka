package com.epam.springproject.test.util;

import com.epam.springproject.dto.CourseDto;
import com.epam.springproject.model.Course;
import com.epam.springproject.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CourseTestDataUtil {

    public static final Long COURSE_ID = 1L;
    public static final String NAME = "CourseTestName";
    public static final String DESCRIPTION = "CourseTestDescription";
    public static final String MATERIALS = "CourseTestMaterials";
    public static final String TASK = "CourseTestTask";
    public static final String DURATION = "CourseTestDuration";

    public static Course createCourse() {
        return Course.builder()
                .courseId(COURSE_ID)
                .name(NAME)
                .description(DESCRIPTION)
                .materials(MATERIALS)
                .task(TASK)
                .duration(DURATION)
                .teacher(UserTestDataUtil.createTeacher())
                .build();
    }

    public static CourseDto createCourseDto() {
        return CourseDto.builder()
                .courseId(COURSE_ID)
                .name(NAME)
                .description(DESCRIPTION)
                .materials(MATERIALS)
                .task(TASK)
                .duration(DURATION)
                .teacher(User.builder().id(UserTestDataUtil.ID).build())
                .build();
    }
}
