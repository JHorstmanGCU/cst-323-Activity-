package com.joelhorstman.cst323.courselog.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "courses")
public class Course {

	// Primary key for the courses table.
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Short course code.
	@NotBlank(message = "Course code is required")
	@Size(max = 20, message = "Course code must be 20 characters or less")
	@Column(name = "course_code", nullable = false, unique = true, length = 20)
	private String courseCode;

	// Full course name.
	@NotBlank(message = "Course name is required")
	@Size(max = 100, message = "Course name must be 100 characters or less")
	@Column(name = "course_name", nullable = false, length = 100)
	private String courseName;

	// Course description.
	@Size(max = 255, message = "Description must be 255 characters or less")
	@Column(length = 255)
	private String description;

	// Number of credit hours.
	@Min(value = 1, message = "Credit hours must be at least 1")
	@Column(name = "credit_hours")
	private Integer creditHours = 3;

	// Teacher assigned to the course.
	@ManyToOne
	@JoinColumn(name = "teacher_id")
	private Teacher teacher;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCreditHours() {
		return creditHours;
	}

	public void setCreditHours(Integer creditHours) {
		this.creditHours = creditHours;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
}
