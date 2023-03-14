package com.example.democrud01.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "FW23_PRODUTO")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String codigo;
	private String descricao;
	
	private BigDecimal precoVenda;
	private BigDecimal precoCusto;
	private BigDecimal estoqueAtual;
	
	
	public Produto(String codigo, String descricao, BigDecimal precoVenda, BigDecimal precoCusto, BigDecimal estoqueAtual) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.precoVenda = precoVenda;
		this.precoCusto = precoCusto;
		this.estoqueAtual = estoqueAtual;
	}
	
}
