package com.example.democrud01.dto;

import java.util.Optional;

import com.example.democrud01.model.SelectUsuario;
import com.example.democrud01.model.SelectUsuarioFiltro;
import com.example.democrud01.service.SelectUsuarioService;
import com.sun.istack.NotNull;

import lombok.Getter;

@Getter
public class SelectUsuarioFiltroForm {

	
	@NotNull
	private String label;
    private String selectFiltro;
    private Long idSelectUsuario;

    public SelectUsuarioFiltro converter(SelectUsuarioService selectUsuarioService) {
    	Optional<SelectUsuario> selectUsuario = selectUsuarioService.get(idSelectUsuario);
    	return new SelectUsuarioFiltro(label, selectFiltro, selectUsuario.get());
    }
}
