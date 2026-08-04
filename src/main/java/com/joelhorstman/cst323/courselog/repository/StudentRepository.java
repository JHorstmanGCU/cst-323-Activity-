package com.joelhorstman.cst323.courselog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.joelhorstman.cst323.courselog.model.Student;

// Gives the application CRUD access to student records.
public interface StudentRepository extends JpaRepository<Student, Long> {
}
