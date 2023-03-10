package com.example.democrud01.dto;

import com.example.democrud01.enums.FormatoPaginaEnum;
import com.example.democrud01.model.SelectUsuario;
import com.sun.istack.NotNull;

import lombok.Getter;

@Getter
public class SelectUsuarioForm {

	
	@NotNull
	private String apelido;
    private String titulo;
    private FormatoPaginaEnum formatoPagina;
    @NotNull
    private String selectUsu;
    private byte[] arquivo;
    private String nomeArquivo;
    private Boolean desativado;

    public SelectUsuario converter() {
    	return new SelectUsuario(apelido, titulo, formatoPagina, selectUsu, arquivo, nomeArquivo, desativado);
    }
}
