package com.joelhorstman.cst323.courselog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joelhorstman.cst323.courselog.model.Course;

// Gives the application CRUD access to course records.
public interface CourseRepository extends JpaRepository<Course, Long> {
}
