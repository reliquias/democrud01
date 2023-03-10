package com.example.democrud01.model;

import java.math.BigDecimal;

import com.example.democrud01.enums.RoleUser;

public class JwtResponse {
	
	private final Long id; 
	private final String name;
	private final String jwttoken;
	private final RoleUser nivel;
	private String jwttokenToSwagger;

	public JwtResponse(Long idUser, String jwttoken, RoleUser nivel, String name) {
		this.id = idUser;
		this.name = name;
		this.jwttoken = jwttoken;
		this.nivel = nivel;
	}

	public String getToken() {
		return this.jwttoken;
	}

	public RoleUser getNivel() {
		return nivel;
	}
	
	
	public String getName() {
		return name;
	}
	
	
	public Long getId() {
		return id;
	}

	public String getJwttokenToSwagger() {
		jwttokenToSwagger = "Bearer " + jwttoken;
		return jwttokenToSwagger;
	}
}
