package com.joelhorstman.cst323.courselog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joelhorstman.cst323.courselog.model.Grade;

// Gives the application CRUD access to grade records.
public interface GradeRepository extends JpaRepository<Grade, Long> {
	Optional<Grade> findByStudentIdAndCourseId(Long studentId, Long courseId);
}
