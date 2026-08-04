package com.joelhorstman.cst323.courselog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.joelhorstman.cst323.courselog.model.Student;
import com.joelhorstman.cst323.courselog.repository.StudentRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/students")
public class StudentController {

	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

	private final StudentRepository studentRepository;

	public StudentController(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	@GetMapping
	public String listStudents(Model model) {
		logger.info("Loading students page");

		// Get all students from the database.
		model.addAttribute("students", studentRepository.findAll());

		// Display Students List View.
		return "students/list";
	}

	@GetMapping("/new")
	public String showCreateForm(Model model) {
		// Create a blank student for the form.
		model.addAttribute("student", new Student());
		model.addAttribute("pageTitle", "Add Student");

		// Display Student Form View.
		return "students/form";
	}

	@GetMapping("/{id}/edit")
	public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
		// Get the selected student from the database.
		Student student = studentRepository.findById(id).orElse(null);

		if (student == null) {
			logger.warn("Student ID {} was not found for editing", id);
			redirectAttributes.addFlashAttribute("errorMessage", "Student was not found.");
			return "redirect:/students";
		}

		model.addAttribute("student", student);
		model.addAttribute("pageTitle", "Edit Student");

		// Display Student Form View.
		return "students/form";
	}

	@PostMapping
	public String saveStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("pageTitle", student.getId() == null ? "Add Student" : "Edit Student");
			return "students/form";
		}

		// Save the student to the database.
		Student savedStudent = studentRepository.save(student);
		logger.info("Saved student ID {}", savedStudent.getId());
		redirectAttributes.addFlashAttribute("successMessage", "Student saved successfully.");

		// Return to the Students List View.
		return "redirect:/students";
	}

	@PostMapping("/{id}/delete")
	public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		if (!studentRepository.existsById(id)) {
			logger.warn("Student ID {} was not found for deletion", id);
			redirectAttributes.addFlashAttribute("errorMessage", "Student was not found.");
			return "redirect:/students";
		}

		// Delete the student from the database.
		studentRepository.deleteById(id);
		logger.info("Deleted student ID {}", id);
		redirectAttributes.addFlashAttribute("successMessage", "Student deleted successfully.");

		// Return to the Students List View.
		return "redirect:/students";
	}
}
