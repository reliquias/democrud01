package com.example.democrud01.dto;

import org.springframework.data.domain.Page;

import com.example.democrud01.enums.FormatoPaginaEnum;
import com.example.democrud01.model.SelectUsuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SelectUsuarioDTO {

	private Long id;
	
	private String apelido;
    private String titulo;
    private FormatoPaginaEnum formatoPagina;
    private String selectUsu;
    private byte[] arquivo;
    private String nomeArquivo;
    private Boolean desativado = Boolean.FALSE;
	
    public SelectUsuarioDTO(SelectUsuario select) {
		this.id = select.getId();
		this.apelido = select.getApelido();
		this.titulo = select.getTitulo();
		this.formatoPagina = select.getFormatoPagina();
		this.selectUsu = select.getSelectUsu();
		this.arquivo = select.getArquivo();
		this.nomeArquivo = select.getNomeArquivo();
		this.desativado = select.getDesativado();
	}
    
    public static Page<SelectUsuarioDTO> converter(Page<SelectUsuario> selects) {
		return selects.map(SelectUsuarioDTO::new);
	}
}
