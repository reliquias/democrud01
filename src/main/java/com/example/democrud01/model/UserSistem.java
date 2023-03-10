package com.example.democrud01.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.democrud01.enums.RoleUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity

@Table(name = "FW23_USUARIO")
public class UserSistem{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	@Column(unique=true)
	private String email;
	private String phone;
	private String password;
	private RoleUser nivel;
	private LocalDateTime dataCadastro = LocalDateTime.now();
	private Boolean desativado;
	
	public UserSistem(String name, String email, String phone, String password, RoleUser nivel,
			LocalDateTime dataCadastro, Boolean desativado) {
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.nivel = nivel;
		this.dataCadastro = dataCadastro;
		this.desativado = desativado;
	}
	
	
	
	

}
