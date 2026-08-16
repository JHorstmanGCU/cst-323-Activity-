package com.joelhorstman.cst323.courselog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.joelhorstman.cst323.courselog.repository.CourseRepository;
import com.joelhorstman.cst323.courselog.repository.GradeRepository;
import com.joelhorstman.cst323.courselog.repository.StudentRepository;
import com.joelhorstman.cst323.courselog.repository.TeacherRepository;

@Controller
public class HomeController {

	private final StudentRepository studentRepository;
	private final TeacherRepository teacherRepository;
	private final CourseRepository courseRepository;
	private final GradeRepository gradeRepository;

	public HomeController(StudentRepository studentRepository, TeacherRepository teacherRepository,
			CourseRepository courseRepository, GradeRepository gradeRepository) {
		this.studentRepository = studentRepository;
		this.teacherRepository = teacherRepository;
		this.courseRepository = courseRepository;
		this.gradeRepository = gradeRepository;
	}

	@GetMapping("/")
	public String home(Model model) {
		// Get database record counts for the home page.
		model.addAttribute("studentCount", studentRepository.count());
		model.addAttribute("teacherCount", teacherRepository.count());
		model.addAttribute("courseCount", courseRepository.count());
		model.addAttribute("gradeCount", gradeRepository.count());

		// Display Home View.
		return "home";
	}
}
