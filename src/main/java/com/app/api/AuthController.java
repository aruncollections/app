package com.app.api;

import com.app.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@PutMapping("authenticate")
	public ResponseEntity<HttpStatus> login() throws Exception {
		log.info("xxxx put");
		return ResponseEntity.ok().build();
	}

	@PostMapping("authenticate")
	public ResponseEntity<HttpStatus> login(@RequestBody User user) throws Exception {
		log.info("xxxx1 User ...");
		Authentication authObject = null;
		try {
			log.info("xxxx1 User {}", user);
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authObject);
		} catch (BadCredentialsException e) {
			throw new Exception("Invalid credentials");
		}
		
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}


	@GetMapping("dashboard/hello")
	public ResponseEntity hello() {
		log.info("xxxx hello");
		return ResponseEntity.ok().body("App running - " + LocalDateTime.now());
	}


}