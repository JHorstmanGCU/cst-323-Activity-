package com.joelhorstman.cst323.courselog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joelhorstman.cst323.courselog.model.Teacher;

// Gives the application CRUD access to teacher records.
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
