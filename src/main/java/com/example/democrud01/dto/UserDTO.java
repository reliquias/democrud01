package com.example.democrud01.dto;

import java.time.LocalDateTime;
import java.util.Calendar;

import org.springframework.data.domain.Page;

import com.example.democrud01.enums.RoleUser;
import com.example.democrud01.model.UserSistem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	
	private Long id;
	private String name;
	private String email;
	private String phone;
	private String password;
	private RoleUser nivel;
	private Calendar dataCadastro = Calendar.getInstance();
	private Boolean desativado;
	
	public UserDTO(UserSistem user) {
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.phone = user.getPhone();
		this.password = user.getPassword();
		this.nivel = user.getNivel();
		this.dataCadastro = user.getDataCadastro();
		this.desativado = user.getDesativado();
	}
	
	
	public static Page<UserDTO> converter(Page<UserSistem> usuarios) {
		return usuarios.map(UserDTO::new);
	}
	


}
