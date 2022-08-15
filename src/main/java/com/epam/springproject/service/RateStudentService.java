package com.epam.springproject.service;

import com.epam.springproject.dto.JournalDto;
import com.epam.springproject.dto.StudentCourseDto;
import com.epam.springproject.model.Journal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RateStudentService {

    Page<StudentCourseDto> findFinishedCourses(Pageable pageable);

    JournalDto rateStudent(JournalDto journalDto);

}
