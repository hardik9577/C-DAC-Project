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

import com.app.dto.AdminResponseDTO;
import com.app.dto.AdminSignupRequest;
import com.app.jwt_utils.JwtUtils;
import com.app.pojos.Student;
import com.app.service.IAdminService;
import com.app.service.IStudentService;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private IAdminService adminService;
	
	@Autowired
	private IStudentService studentService;
	
	@PostMapping("/register")
	public ResponseEntity<AdminResponseDTO> registerAdmin(@RequestBody AdminSignupRequest adminRequest) {
		System.out.println("in admin reg : admin request " + adminRequest);
		return ResponseEntity.ok(adminService.registerAdmin(adminRequest));
	}
	
	@GetMapping("/list")
	public List<Student> fetchAllStudents() {
		System.out.println("in fetch all students");
		return studentService.getAllStudents();
	}
}
