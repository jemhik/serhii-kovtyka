package com.epam.springproject.service.impl;

import com.epam.springproject.dto.JournalDto;
import com.epam.springproject.dto.StudentCourseDto;
import com.epam.springproject.mapper.JournalMapper;
import com.epam.springproject.mapper.StudentCourseMapper;
import com.epam.springproject.model.Journal;
import com.epam.springproject.repository.JournalRepository;
import com.epam.springproject.repository.StudentCourseRepository;
import com.epam.springproject.service.RateStudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RateStudentServiceImpl implements RateStudentService {

    private final StudentCourseRepository studentCourseRepository;
    private final JournalRepository journalRepository;

    @Override
    public Page<StudentCourseDto> findFinishedCourses(Pageable pageable) {
        return studentCourseRepository.findFinishedCourses("submitted", pageable)
                .map(StudentCourseMapper.INSTANCE::mapStudentCourseToStudentCourseDto);
    }

    @Override
    @Transactional
    public JournalDto rateStudent(JournalDto journalDto) {
        studentCourseRepository.endCourse("ended", journalDto.getStudentCourse().getStudentCourseId());
        journalDto.setStudentCourse(studentCourseRepository.getById(journalDto.getStudentCourse().getStudentCourseId()));
        return JournalMapper.INSTANCE.mapJournalToJournalDto(
                journalRepository.save(
                        JournalMapper.INSTANCE.mapJournalDtoToJournal(journalDto)));
    }

}
