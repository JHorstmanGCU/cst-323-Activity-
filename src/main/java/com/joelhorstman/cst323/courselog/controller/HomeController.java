package com.joelhorstman.cst323.courselog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.joelhorstman.cst323.courselog.repository.StudentRepository;

@Controller
public class HomeController {

	private final StudentRepository studentRepository;

	public HomeController(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	@GetMapping("/")
	public String home(Model model) {
		// Get the number of students for the home page.
		model.addAttribute("studentCount", studentRepository.count());

		// Display Home View.
		return "home";
	}
}
