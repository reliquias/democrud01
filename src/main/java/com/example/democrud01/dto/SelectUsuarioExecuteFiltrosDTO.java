package com.example.democrud01.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SelectUsuarioExecuteFiltrosDTO {
	
	private String label;
	private String conteudo;
	private String tipo = "";
	
}