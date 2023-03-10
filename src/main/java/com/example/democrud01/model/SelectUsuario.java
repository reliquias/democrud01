package com.example.democrud01.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.example.democrud01.enums.FormatoPaginaEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "FW23_SELECT_USUARIO")
public class SelectUsuario{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String apelido;
    private String titulo;
    
    private FormatoPaginaEnum formatoPagina;
    
    @Lob
    private String selectUsu;
    
    @Lob
    private byte[] arquivo;
    
    private String nomeArquivo;
    
    private Boolean desativado = Boolean.FALSE;

	public SelectUsuario(String apelido, String titulo, FormatoPaginaEnum formatoPagina, String selectUsu,
			byte[] arquivo, String nomeArquivo, Boolean desativado) {
		this.apelido = apelido;
		this.titulo = titulo;
		this.formatoPagina = formatoPagina;
		this.selectUsu = selectUsu;
		this.arquivo = arquivo;
		this.nomeArquivo = nomeArquivo;
		this.desativado = desativado;
	}
}
