package com.joelhorstman.cst323.courselog.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "grades", uniqueConstraints = @UniqueConstraint(name = "uq_student_course", columnNames = { "student_id",
		"course_id" }))
public class Grade {

	// Primary key for the grades table.
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Student connected to this grade.
	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;

	// Course connected to this grade.
	@ManyToOne
	@JoinColumn(name = "course_id", nullable = false)
	private Course course;

	// Overall numeric grade.
	@NotNull(message = "Overall grade is required")
	@DecimalMin(value = "0.00", message = "Grade must be at least 0")
	@DecimalMax(value = "100.00", message = "Grade cannot be over 100")
	@Column(name = "overall_grade", nullable = false, precision = 5, scale = 2)
	private BigDecimal overallGrade;

	// Letter grade.
	@Size(max = 2, message = "Letter grade must be 2 characters or less")
	@Column(name = "letter_grade", length = 2)
	private String letterGrade;

	// Optional grade comments.
	@Size(max = 255, message = "Comments must be 255 characters or less")
	@Column(length = 255)
	private String comments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public BigDecimal getOverallGrade() {
		return overallGrade;
	}

	public void setOverallGrade(BigDecimal overallGrade) {
		this.overallGrade = overallGrade;
	}

	public String getLetterGrade() {
		return letterGrade;
	}

	public void setLetterGrade(String letterGrade) {
		this.letterGrade = letterGrade;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
