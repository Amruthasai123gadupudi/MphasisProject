package com.realestate.controller;
 
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.model.User;
import com.realestate.service.UserService;
import com.realestate.util.JwtUtil;
 
@RestController
@RequestMapping("/api/auth")
public class AuthController {
 
	@Autowired
	private UserService userService;
 
	@PostMapping("/register")// Register API
	public ResponseEntity<?> registerUser(@RequestBody User user) {
		System.out.println("Received user: " + user);
		User savedUser = userService.registerUser(user);
 
		if (savedUser != null) {
			savedUser.setPassword(null);
			return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("user already exists", HttpStatus.CONFLICT);
		}
	}
 
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam("email") String email, @RequestParam("password") String password) {
	    User user = userService.loginUser(email, password);

	    if (user != null) {
	        String token = JwtUtil.generateToken(user.getEmail(), user.getRole().name());
	        // Avoid sending password back
	        user.setPassword(null);
	        Map<String, Object> response = Map.of(
	            "token", token,
	            "user", user
	        );
	        return ResponseEntity.ok(response);
	    } else {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	    }
	}


 
	@GetMapping("/users")// Get All Users
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();
 
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
 
}