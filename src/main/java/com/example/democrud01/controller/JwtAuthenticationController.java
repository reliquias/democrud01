package com.example.democrud01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.democrud01.conf.JwtTokenUtil;
import com.example.democrud01.model.JwtRequest;
import com.example.democrud01.model.JwtResponse;
import com.example.democrud01.service.JwtUserDetailsService;

@RestController
@RequestMapping({"/api/authenticate"})
@CrossOrigin(origins = {"http://localhost:4200/", "http://www.505crm.com.br/"}, maxAge = 3600, allowCredentials = "true", allowedHeaders = "*")
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@GetMapping(path= {"/teste"})
	public String teste() {
		return "chegou no destino";
	}

	@PostMapping(path= {"/testePost"})
	public String testePost() {
		return "chegou no destino testePost";
	}

	@PostMapping
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		System.out.println("chagou a tentar autenticar");
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(userDetailsService.getUserSistem().getId(), token, userDetailsService.getUserSistem().getNivel(), userDetailsService.getUserSistem().getName()));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}