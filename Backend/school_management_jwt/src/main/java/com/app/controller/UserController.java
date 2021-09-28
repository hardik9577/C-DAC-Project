package com.app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.AdminResponseDTO;
import com.app.dto.AdminSignupRequest;
import com.app.dto.AuthenticationRequest;
import com.app.dto.AuthenticationResponse;
import com.app.dto.StudentResponseDTO;
import com.app.dto.StudentSignupRequest;
import com.app.jwt_utils.JwtUtils;
import com.app.pojos.Student;
import com.app.service.IAdminService;
import com.app.service.IStudentService;
import com.app.service.UserDetailsImpl;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

	// auto wire Authentication Manager for user authentication , created in
	// Security Config class
	// (currently based upon user details service)
	@Autowired
	private AuthenticationManager authManager;
	// auto wire JwtUtils for sending signed JWT back to the clnt
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private IStudentService studentService;
	
	@Autowired
	private IAdminService adminService;
	
	
	@PostMapping("/signup/admin")
	public ResponseEntity<AdminResponseDTO> registerAdmin(@RequestBody AdminSignupRequest adminRequest) {
		System.out.println("in admin reg : admin request " + adminRequest);
		return ResponseEntity.ok(adminService.registerAdmin(adminRequest));
	}
	
	@PostMapping("/signup/student")
	public ResponseEntity<StudentResponseDTO> registerStudent(@RequestBody StudentSignupRequest studentRequest) {
		System.out.println("in student reg : student request " + studentRequest);
		return ResponseEntity.ok(studentService.registerStudent(studentRequest));
	}
	
	@GetMapping("/student/list")
	public List<Student> fetchAllStudents() {
		System.out.println("in fetch all students");
		return studentService.getAllStudents();
	}
	
	@GetMapping("/student/{id}")
	public ResponseEntity<?> getUserDetails(@PathVariable int id) {
		System.out.println("in get Student details " +id);
		return ResponseEntity.ok(studentService.getDetails(id));
	}
	
	@DeleteMapping("/student/{studentId}")
	public ResponseEntity<String> deleteStudentDetails(@PathVariable int studentId) {
		System.out.println("in delete Student details " + studentId);
		return ResponseEntity.ok(studentService.deleteStudent(studentId));
	}
	
	@PutMapping("/student/{id}")
	public ResponseEntity<?> updateStudentDetails(@RequestBody Student student, @PathVariable int id) {
		System.out.println("in update Student " + student + " " + id);
		Student existingStudent = studentService.getDetails(id);
		System.out.println(existingStudent);
		return ResponseEntity.ok(studentService.updateDetails(student));
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest request) {
		System.out.println("in auth " + request);
		try {
			// Tries to authenticate the passed Authentication object, returning a fully
			// populated Authentication object (including granted authorities)if successful.
			Authentication authenticate = authManager.authenticate(
					// An o.s.s.c.Authentication i/f implementation used for simple presentation of
					// a username and password.
					// Actual dao based authentication takes place here internally(first username n
					// then pwd n then authorities gets validated)
					new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
			// => successful authentication : create JWT n send it to the clnt in the
			// response.
			System.out.println("auth success " + authenticate);
			SecurityContextHolder.getContext().setAuthentication(authenticate);
			String jwt = jwtUtils.generateJwtToken(authenticate);
			UserDetailsImpl userDetails = (UserDetailsImpl) authenticate.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream()
					.map(item -> item.getAuthority())
					.collect(Collectors.toList());
			return ResponseEntity.ok(new AuthenticationResponse(jwt, userDetails.getId(), userDetails.getUsername(),
					userDetails.getEmail(), roles));

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("User authentication Failed", e);
		}
	}
}
