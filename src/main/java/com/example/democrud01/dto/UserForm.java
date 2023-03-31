package com.example.democrud01.dto;

import com.example.democrud01.enums.RoleUser;
import com.example.democrud01.model.UserSistem;
import com.sun.istack.NotNull;

import lombok.Getter;

@Getter
public class UserForm {
	
	@NotNull
	private String name;
	@NotNull
	private String email;
	private String phone;
	private RoleUser nivel;
	private String pix;
	private Boolean desativado;
	private String password;

	public UserSistem converter() {
    	return new UserSistem(name, email, phone, password, nivel, desativado);
    }
	


}
