package com.epam.springproject.service;

import com.epam.springproject.dto.JournalDto;
import com.epam.springproject.dto.StudentCourseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RateStudentService {

    Page<StudentCourseDto> findFinishedCourses(Pageable pageable);

    JournalDto rateStudent(JournalDto journalDto);

}
