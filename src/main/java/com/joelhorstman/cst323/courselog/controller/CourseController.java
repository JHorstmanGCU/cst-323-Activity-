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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.joelhorstman.cst323.courselog.model.Course;
import com.joelhorstman.cst323.courselog.model.Teacher;
import com.joelhorstman.cst323.courselog.repository.CourseRepository;
import com.joelhorstman.cst323.courselog.repository.TeacherRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/courses")
public class CourseController {

	private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

	private final CourseRepository courseRepository;
	private final TeacherRepository teacherRepository;

	public CourseController(CourseRepository courseRepository, TeacherRepository teacherRepository) {
		this.courseRepository = courseRepository;
		this.teacherRepository = teacherRepository;
	}

	@GetMapping
	public String listCourses(Model model) {
		logger.info("Loading courses page");

		// Get all courses from the database.
		model.addAttribute("courses", courseRepository.findAll());

		// Display Courses List View.
		return "courses/list";
	}

	@GetMapping("/new")
	public String showCreateForm(Model model) {
		// Create a blank course for the form.
		model.addAttribute("course", new Course());
		addCourseFormLists(model);
		model.addAttribute("pageTitle", "Add Course");

		// Display Course Form View.
		return "courses/form";
	}

	@GetMapping("/{id}/edit")
	public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
		// Get the selected course from the database.
		Course course = courseRepository.findById(id).orElse(null);

		if (course == null) {
			logger.warn("Course ID {} was not found for editing", id);
			redirectAttributes.addFlashAttribute("errorMessage", "Course was not found.");
			return "redirect:/courses";
		}

		model.addAttribute("course", course);
		addCourseFormLists(model);
		model.addAttribute("pageTitle", "Edit Course");

		// Display Course Form View.
		return "courses/form";
	}

	@PostMapping
	public String saveCourse(@Valid @ModelAttribute("course") Course course, BindingResult bindingResult,
			@RequestParam(required = false) Long teacherId, Model model, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			addCourseFormLists(model);
			model.addAttribute("pageTitle", course.getId() == null ? "Add Course" : "Edit Course");
			return "courses/form";
		}

		// Assign the selected teacher to the course.
		Teacher teacher = null;
		if (teacherId != null) {
			teacher = teacherRepository.findById(teacherId).orElse(null);
		}
		course.setTeacher(teacher);

		// Save the course to the database.
		Course savedCourse = courseRepository.save(course);
		logger.info("Saved course ID {}", savedCourse.getId());
		redirectAttributes.addFlashAttribute("successMessage", "Course saved successfully.");

		// Return to the Courses List View.
		return "redirect:/courses";
	}

	@PostMapping("/{id}/delete")
	public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		if (!courseRepository.existsById(id)) {
			logger.warn("Course ID {} was not found for deletion", id);
			redirectAttributes.addFlashAttribute("errorMessage", "Course was not found.");
			return "redirect:/courses";
		}

		// Delete the course from the database.
		courseRepository.deleteById(id);
		logger.info("Deleted course ID {}", id);
		redirectAttributes.addFlashAttribute("successMessage", "Course deleted successfully.");

		// Return to the Courses List View.
		return "redirect:/courses";
	}

	private void addCourseFormLists(Model model) {
		// Get teachers for the course form dropdown.
		model.addAttribute("teachers", teacherRepository.findAll());
	}
}
