package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dao.StudentRepository;
import com.app.jwt_utils.JwtUtils;
import com.app.pojos.Feedback;
import com.app.service.IFeedbackService;
import com.app.service.IStudentService;

@CrossOrigin
@RestController
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private IStudentService studentService;
	
	@Autowired
	private StudentRepository studentRepo;
	
	@Autowired
	private IFeedbackService feedbackService;
	
	@GetMapping("/feedback/{studentId}") 
	public List<Feedback> fetchAllFeedbacks(@PathVariable int studentId) {
		System.out.println("in fetch all Feedbacks for student id: " + studentId);
		return feedbackService.getAllFeedbacks(studentId);
	}
}
