package com.example.democrud01.dto;

import java.math.BigDecimal;

import com.example.democrud01.model.Produto;
import com.sun.istack.NotNull;

import lombok.Getter;

@Getter
public class ProdutoForm {

	
	@NotNull
	private String codigo;
	private String descricao;
	
	private BigDecimal precoVenda;
	private BigDecimal precoCusto;
	private BigDecimal estoqueAtual;

    public Produto converter() {
    	return new Produto(codigo,descricao,precoVenda,precoCusto,estoqueAtual);
    }
}
