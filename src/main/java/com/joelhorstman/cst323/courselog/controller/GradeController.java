package com.joelhorstman.cst323.courselog.controller;

import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.joelhorstman.cst323.courselog.model.Course;
import com.joelhorstman.cst323.courselog.model.Grade;
import com.joelhorstman.cst323.courselog.model.Student;
import com.joelhorstman.cst323.courselog.repository.CourseRepository;
import com.joelhorstman.cst323.courselog.repository.GradeRepository;
import com.joelhorstman.cst323.courselog.repository.StudentRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/grades")
public class GradeController {

	private static final Logger logger = LoggerFactory.getLogger(GradeController.class);

	private final GradeRepository gradeRepository;
	private final StudentRepository studentRepository;
	private final CourseRepository courseRepository;

	public GradeController(GradeRepository gradeRepository, StudentRepository studentRepository,
			CourseRepository courseRepository) {
		this.gradeRepository = gradeRepository;
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;
	}

	@GetMapping
	public String listGrades(Model model) {
		logger.info("Loading grades page");

		// Get all grades from the database.
		model.addAttribute("grades", gradeRepository.findAll());

		// Display Grades List View.
		return "grades/list";
	}

	@GetMapping("/new")
	public String showCreateForm(Model model) {
		// Create a blank grade for the form.
		model.addAttribute("grade", new Grade());
		addGradeFormLists(model);
		model.addAttribute("pageTitle", "Add Grade");

		// Display Grade Form View.
		return "grades/form";
	}

	@GetMapping("/{id}/edit")
	public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
		// Get the selected grade from the database.
		Grade grade = gradeRepository.findById(id).orElse(null);

		if (grade == null) {
			logger.warn("Grade ID {} was not found for editing", id);
			redirectAttributes.addFlashAttribute("errorMessage", "Grade was not found.");
			return "redirect:/grades";
		}

		model.addAttribute("grade", grade);
		addGradeFormLists(model);
		model.addAttribute("pageTitle", "Edit Grade");

		// Display Grade Form View.
		return "grades/form";
	}

	@PostMapping
	public String saveGrade(@Valid @ModelAttribute("grade") Grade grade, BindingResult bindingResult,
			@RequestParam Long studentId, @RequestParam Long courseId, Model model,
			RedirectAttributes redirectAttributes) {
		Student student = studentRepository.findById(studentId).orElse(null);
		Course course = courseRepository.findById(courseId).orElse(null);

		if (student == null || course == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "Student or course was not found.");
			return "redirect:/grades";
		}

		// Assign the selected student and course to the grade.
		grade.setStudent(student);
		grade.setCourse(course);

		if (bindingResult.hasErrors()) {
			addGradeFormLists(model);
			model.addAttribute("pageTitle", grade.getId() == null ? "Add Grade" : "Edit Grade");
			return "grades/form";
		}

		// Check for an existing grade for the same student and course.
		Optional<Grade> existingGrade = gradeRepository.findByStudentIdAndCourseId(studentId, courseId);
		if (existingGrade.isPresent() && grade.getId() == null) {
			grade.setId(existingGrade.get().getId());
		} else if (existingGrade.isPresent() && !existingGrade.get().getId().equals(grade.getId())) {
			logger.warn("Duplicate grade was entered for student ID {} and course ID {}", studentId, courseId);
			addGradeFormLists(model);
			model.addAttribute("pageTitle", grade.getId() == null ? "Add Grade" : "Edit Grade");
			model.addAttribute("errorMessage", "A grade already exists for this student and course. Edit the existing grade instead.");
			return "grades/form";
		}

		// Save the grade to the database.
		Grade savedGrade = gradeRepository.save(grade);
		logger.info("Saved grade ID {}", savedGrade.getId());
		redirectAttributes.addFlashAttribute("successMessage", "Grade saved successfully.");

		// Return to the Grades List View.
		return "redirect:/grades";
	}

	@PostMapping("/{id}/delete")
	public String deleteGrade(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		if (!gradeRepository.existsById(id)) {
			logger.warn("Grade ID {} was not found for deletion", id);
			redirectAttributes.addFlashAttribute("errorMessage", "Grade was not found.");
			return "redirect:/grades";
		}

		// Delete the grade from the database.
		gradeRepository.deleteById(id);
		logger.info("Deleted grade ID {}", id);
		redirectAttributes.addFlashAttribute("successMessage", "Grade deleted successfully.");

		// Return to the Grades List View.
		return "redirect:/grades";
	}

	private void addGradeFormLists(Model model) {
		// Get students and courses for the grade form dropdowns.
		model.addAttribute("students", studentRepository.findAll());
		model.addAttribute("courses", courseRepository.findAll());
	}
}
