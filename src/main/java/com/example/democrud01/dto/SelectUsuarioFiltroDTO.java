package com.example.democrud01.dto;

import org.springframework.data.domain.Page;

import com.example.democrud01.model.SelectUsuarioFiltro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SelectUsuarioFiltroDTO {

	private Long id;
	
	private String label;
    private String selectFiltro;
    private Long idSelectUsuario;
	
    public SelectUsuarioFiltroDTO(SelectUsuarioFiltro filtro) {
		this.id = filtro.getId();
		this.label = filtro.getLabel();
		this.selectFiltro = filtro.getSelectFiltro();
		this.idSelectUsuario = filtro.getSelectUsuario().getId();
	}
    
    public static Page<SelectUsuarioFiltroDTO> converter(Page<SelectUsuarioFiltro> filtros) {
		return filtros.map(SelectUsuarioFiltroDTO::new);
	}
}
