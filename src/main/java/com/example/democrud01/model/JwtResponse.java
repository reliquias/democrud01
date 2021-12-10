package com.example.democrud01.model;

import com.example.democrud01.enums.RoleUser;

public class JwtResponse {
	
	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final RoleUser roleUser;

	public JwtResponse(String jwttoken, RoleUser roleUser) {
		this.jwttoken = jwttoken;
		this.roleUser = roleUser;
	}

	public String getToken() {
		return this.jwttoken;
	}

	public RoleUser getRoleUser() {
		return roleUser;
	}
}
