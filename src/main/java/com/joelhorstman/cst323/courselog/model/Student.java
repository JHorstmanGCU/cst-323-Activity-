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
@Table(name = "students")
public class Student {

	// Primary key for the students table.
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Student first name.
	@NotBlank(message = "First name is required")
	@Size(max = 50, message = "First name must be 50 characters or less")
	@Column(name = "first_name", nullable = false, length = 50)
	private String firstName;

	// Student last name.
	@NotBlank(message = "Last name is required")
	@Size(max = 50, message = "Last name must be 50 characters or less")
	@Column(name = "last_name", nullable = false, length = 50)
	private String lastName;

	// Student email address.
	@NotBlank(message = "Email is required")
	@Email(message = "Enter a valid email address")
	@Size(max = 100, message = "Email must be 100 characters or less")
	@Column(nullable = false, unique = true, length = 100)
	private String email;

	// Shows if the student is active.
	@Column(nullable = false)
	private Boolean active = true;

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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
