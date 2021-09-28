package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
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
import com.app.service.IAdminService;
import com.app.service.IStudentService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserSignupSignInController {

	// auto wire Authentication Manager for user authentication , created in
	// Security Config class
	// (currently based upon user details service)
	@Autowired
	private AuthenticationManager authManager;
	// auto wire JwtUtils for sending signed JWT back to the clnt
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private IAdminService adminService;
	
	@Autowired
	private IStudentService studentService;

	@PostMapping("/signup/admin")
	public ResponseEntity<AdminResponseDTO> registerAdmin(@RequestBody AdminSignupRequest adminRequest) {
		System.out.println("in admin reg : admin request " + adminRequest);
		return ResponseEntity.ok(adminService.registerAdmin(adminRequest));
	}
	
//	@PostMapping("/signup/admin")
//	public ResponseEntity<StudentResponseDTO> registerStudent(@RequestBody StudentSignupRequest studentRequest) {
//		System.out.println("in student reg : student request " +studentRequest);
//		return ResponseEntity.ok(studentService.registerStudent(studentRequest));
//	}

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
			return ResponseEntity.ok(new AuthenticationResponse(jwtUtils.generateJwtToken(authenticate)));

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("User authentication Failed", e);
		}
	}
}
