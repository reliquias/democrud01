package com.example.democrud01.dto;

import com.example.democrud01.enums.TipoAgente;
import com.example.democrud01.model.Agente;
import com.sun.istack.NotNull;

import lombok.Getter;

@Getter
public class AgenteForm {
	
	@NotNull
	private String name;
	@NotNull
	private String email;
	private String phone;
	private TipoAgente tipo;
	private Boolean desativado;

	public Agente converter() {
    	return new Agente(tipo, name, email, phone, desativado);
    }
	


}
