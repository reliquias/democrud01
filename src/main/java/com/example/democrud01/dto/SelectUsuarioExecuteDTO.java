package com.example.democrud01.dto;

import java.util.List;

import com.example.democrud01.enums.TipoDocumento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SelectUsuarioExecuteDTO {
	
	private Long idRelatorio;
	private TipoDocumento tipoDocumento;
	private List<SelectUsuarioExecuteFiltrosDTO> filtros; 
}