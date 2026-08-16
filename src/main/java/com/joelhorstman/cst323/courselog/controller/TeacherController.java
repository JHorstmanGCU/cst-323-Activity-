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

import com.joelhorstman.cst323.courselog.model.Teacher;
import com.joelhorstman.cst323.courselog.repository.TeacherRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

	private static final Logger logger = LoggerFactory.getLogger(TeacherController.class);

	private final TeacherRepository teacherRepository;

	public TeacherController(TeacherRepository teacherRepository) {
		this.teacherRepository = teacherRepository;
	}

	@GetMapping
	public String listTeachers(Model model) {
		logger.info("Loading teachers page");

		// Get all teachers from the database.
		model.addAttribute("teachers", teacherRepository.findAll());

		// Display Teachers List View.
		return "teachers/list";
	}

	@GetMapping("/new")
	public String showCreateForm(Model model) {
		// Create a blank teacher for the form.
		model.addAttribute("teacher", new Teacher());
		model.addAttribute("pageTitle", "Add Teacher");

		// Display Teacher Form View.
		return "teachers/form";
	}

	@GetMapping("/{id}/edit")
	public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
		// Get the selected teacher from the database.
		Teacher teacher = teacherRepository.findById(id).orElse(null);

		if (teacher == null) {
			logger.warn("Teacher ID {} was not found for editing", id);
			redirectAttributes.addFlashAttribute("errorMessage", "Teacher was not found.");
			return "redirect:/teachers";
		}

		model.addAttribute("teacher", teacher);
		model.addAttribute("pageTitle", "Edit Teacher");

		// Display Teacher Form View.
		return "teachers/form";
	}

	@PostMapping
	public String saveTeacher(@Valid @ModelAttribute("teacher") Teacher teacher, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("pageTitle", teacher.getId() == null ? "Add Teacher" : "Edit Teacher");
			return "teachers/form";
		}

		// Save the teacher to the database.
		Teacher savedTeacher = teacherRepository.save(teacher);
		logger.info("Saved teacher ID {}", savedTeacher.getId());
		redirectAttributes.addFlashAttribute("successMessage", "Teacher saved successfully.");

		// Return to the Teachers List View.
		return "redirect:/teachers";
	}

	@PostMapping("/{id}/delete")
	public String deleteTeacher(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		if (!teacherRepository.existsById(id)) {
			logger.warn("Teacher ID {} was not found for deletion", id);
			redirectAttributes.addFlashAttribute("errorMessage", "Teacher was not found.");
			return "redirect:/teachers";
		}

		// Delete the teacher from the database.
		teacherRepository.deleteById(id);
		logger.info("Deleted teacher ID {}", id);
		redirectAttributes.addFlashAttribute("successMessage", "Teacher deleted successfully.");

		// Return to the Teachers List View.
		return "redirect:/teachers";
	}
}
