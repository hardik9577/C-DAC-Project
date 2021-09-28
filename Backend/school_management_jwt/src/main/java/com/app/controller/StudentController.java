package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dao.StudentRepository;
import com.app.dto.StudentResponseDTO;
import com.app.dto.StudentSignupRequest;
import com.app.jwt_utils.JwtUtils;
import com.app.pojos.Student;
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
	
	@GetMapping("/list")
	public List<Student> fetchAllStudents() {
		System.out.println("in fetch all students");
		return studentRepo.findAll();
	}
	
	@PostMapping("/signup")
	public ResponseEntity<StudentResponseDTO> registerStudent(@RequestBody StudentSignupRequest studentRequest) {
		System.out.println("in student reg : student request " + studentRequest);
		return ResponseEntity.ok(studentService.registerStudent(studentRequest));
	}
}
