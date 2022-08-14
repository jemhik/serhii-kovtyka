package com.epam.springproject.repository;

import com.epam.springproject.model.StudentCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {

    Page<StudentCourse> findByStudentId(Long studentId, Pageable pageable);

}
