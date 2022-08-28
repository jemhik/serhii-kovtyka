package com.epam.springproject.service;

import com.epam.springproject.dto.JournalDto;
import com.epam.springproject.dto.StudentCourseDto;
import com.epam.springproject.mapper.StudentCourseMapper;
import com.epam.springproject.model.Journal;
import com.epam.springproject.model.StudentCourse;
import com.epam.springproject.repository.JournalRepository;
import com.epam.springproject.repository.StudentCourseRepository;
import com.epam.springproject.service.impl.RateStudentServiceImpl;
import com.epam.springproject.test.util.StudentCourseDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RateStudentServiceImplTest {
    @InjectMocks
    private RateStudentServiceImpl rateStudentService;
    @Mock
    private StudentCourseRepository studentCourseRepository;
    @Mock
    private JournalRepository journalRepository;

    @Test
    void findFinishedCoursesTest() {
        StudentCourseDto studentCourseDto1 = StudentCourseDataUtil
                .createStudentCourseDto(StudentCourseDataUtil.COURSE_PROGRESS_SUBMITTED);

        StudentCourseDto studentCourseDto2 = StudentCourseDataUtil
                .createStudentCourseDto(StudentCourseDataUtil.COURSE_PROGRESS_SUBMITTED);
        studentCourseDto2.setStudentCourseId(2L);

        StudentCourseDto studentCourseDto3 = StudentCourseDataUtil
                .createStudentCourseDto(StudentCourseDataUtil.COURSE_PROGRESS_SUBMITTED);
        studentCourseDto3.setStudentCourseId(3L);

        List<StudentCourse> studentCourses =
                Arrays.asList(
                        StudentCourseMapper.INSTANCE.mapStudentCourseDtoToStudentCourse(studentCourseDto1),
                        StudentCourseMapper.INSTANCE.mapStudentCourseDtoToStudentCourse(studentCourseDto2),
                        StudentCourseMapper.INSTANCE.mapStudentCourseDtoToStudentCourse(studentCourseDto3));

        Pageable pageable = PageRequest.of(0, 2);

        when(studentCourseRepository.findFinishedCourses("submitted", pageable))
                .thenReturn(new PageImpl<>(studentCourses.subList(0, 2), pageable, studentCourses.size()));

        Page<StudentCourseDto> pagedStudentCourses = rateStudentService.findFinishedCourses(pageable);

        assertThat(pagedStudentCourses.getSize(), is(2));
        assertThat(pagedStudentCourses.getPageable(), is(pageable));
        assertThat(pagedStudentCourses.getContent(), hasItems(studentCourseDto1, studentCourseDto2));

        verify(studentCourseRepository, times(1))
                .findFinishedCourses("submitted", pageable);
    }

    @Test
    void rateStudentTest() {
        StudentCourse studentCourse = StudentCourseDataUtil
                .createStudentCourse(StudentCourseDataUtil.COURSE_PROGRESS_SUBMITTED);
        JournalDto testJournalDto = JournalDto.builder()
                .assessmentId(1L)
                .assessment(10L)
                .studentCourse(StudentCourse.builder().studentCourseId(1L).build())
                .build();
        Journal journal = Journal.builder().assessmentId(1L)
                .assessment(10L)
                .studentCourse(StudentCourseDataUtil
                        .createStudentCourse(StudentCourseDataUtil.COURSE_PROGRESS_SUBMITTED))
                .build();

        when(studentCourseRepository.getById(studentCourse.getStudentCourseId()))
                .thenReturn(studentCourse);
        when(journalRepository.save(journal)).thenReturn(journal);

        JournalDto journalDto = rateStudentService.rateStudent(testJournalDto);

        verify(studentCourseRepository, times(1))
                .endCourse("ended", journal.getStudentCourse().getStudentCourseId());

        assertThat(journalDto, allOf(
                hasProperty("assessmentId", equalTo(journal.getAssessmentId())),
                hasProperty("assessment", equalTo(journal.getAssessment()))
        ));
    }
}
