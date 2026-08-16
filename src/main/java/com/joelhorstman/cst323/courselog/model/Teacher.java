package com.joelhorstman.cst323.courselog.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "teachers")
public class Teacher {

	// Primary key for the teachers table.
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Teacher first name.
	@NotBlank(message = "First name is required")
	@Size(max = 50, message = "First name must be 50 characters or less")
	@Column(name = "first_name", nullable = false, length = 50)
	private String firstName;

	// Teacher last name.
	@NotBlank(message = "Last name is required")
	@Size(max = 50, message = "Last name must be 50 characters or less")
	@Column(name = "last_name", nullable = false, length = 50)
	private String lastName;

	// Teacher email address.
	@NotBlank(message = "Email is required")
	@Email(message = "Enter a valid email address")
	@Size(max = 100, message = "Email must be 100 characters or less")
	@Column(nullable = false, unique = true, length = 100)
	private String email;

	// Teacher department.
	@Size(max = 100, message = "Department must be 100 characters or less")
	@Column(length = 100)
	private String department;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
}
