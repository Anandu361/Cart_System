package com.example.cart.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.cart.Repository.UserRepository;
import com.example.cart.Model.User;
import com.example.cart.Security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	private org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtil jwtutil;
	
	@PostMapping("/register")
	public String Register(@RequestBody User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return "User registered Successfully";
	}
	
	@PostMapping("/login")
	public String Login(@RequestBody User user) {
		User dbuser = userRepository.findByEmail(user.getEmail());
		
		if (dbuser == null) {
			return "User not found";
		}
		
		boolean match = passwordEncoder.matches(user.getPassword(), dbuser.getPassword());
		
		if(!match) {
			return "Invalid password!";
		}
		
		String token = jwtutil.generateToken(dbuser.getUsername());
		
		return token;
	}
}