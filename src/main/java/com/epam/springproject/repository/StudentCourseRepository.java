package com.epam.springproject.repository;

import com.epam.springproject.model.StudentCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {

    Page<StudentCourse> findByStudentId(Long studentId, Pageable pageable);

    @Modifying
    @Query("update StudentCourse sc set sc.studentSolution = ?1, sc.progress = ?2 where sc.studentCourseId = ?3")
    void finishCourse(String studentSolution, String progress, Long studentCourseId);

    @Query("select sc from StudentCourse sc where sc.progress = ?1")
    Page<StudentCourse> findFinishedCourses(String progress, Pageable pageable);

    @Modifying
    @Query("update StudentCourse sc set sc.progress = ?1 where sc.studentCourseId = ?2")
    void endCourse(String progress, Long studentCourseId);

}
