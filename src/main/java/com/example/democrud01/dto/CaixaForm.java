package com.example.democrud01.dto;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Optional;

import com.example.democrud01.model.Caixa;
import com.example.democrud01.model.UserSistem;
import com.example.democrud01.service.UserService;

import lombok.Getter;

@Getter
public class CaixaForm {
	
	
	private Long idUsuarioAbertura;
	private Long idUsuarioFechamento;
	private Calendar dataAbertura;
	private Calendar dataFechamento;
	private BigDecimal valorTotal;
		
	public Caixa converter(UserService userService) {
		Optional<UserSistem> usuarioAbertura = userService.get(idUsuarioAbertura);
		Optional<UserSistem> usuarioFechamento = userService.get(idUsuarioFechamento);
		return new Caixa(usuarioAbertura.get(), dataAbertura, usuarioFechamento.get(), dataFechamento, valorTotal);
    }
	
	

}
