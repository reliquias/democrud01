package com.example.democrud01.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class BeanSelectUsuarioCampos {
	
	private Long id;
	private String label;
	private String conteudo;
	private String tamanho = "30";
	private String tipo = "";
	private List<BeanOptions> lista;
	private Map<String, String> listMap = new HashMap<>();
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public String getTamanho() {
		return tamanho;
	}

	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((conteudo == null) ? 0 : conteudo.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((tamanho == null) ? 0 : tamanho.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BeanSelectUsuarioCampos other = (BeanSelectUsuarioCampos) obj;
		if (conteudo == null) {
			if (other.conteudo != null)
				return false;
		} else if (!conteudo.equals(other.conteudo))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (tamanho == null) {
			if (other.tamanho != null)
				return false;
		} else if (!tamanho.equals(other.tamanho))
			return false;
		return true;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public List<BeanOptions> getLista() {
		return lista;
	}

	public void setLista(List<BeanOptions> lista) {
		this.lista = lista;
	}

	public Map<String, String> getListMap() {
		return listMap;
	}

	public void setListMap(Map<String, String> listMap) {
		this.listMap = listMap;
	}
}