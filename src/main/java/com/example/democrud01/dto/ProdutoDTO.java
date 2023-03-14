package com.example.democrud01.dto;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;

import com.example.democrud01.model.Produto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

	private Long id;

	private String codigo;
	private String descricao;
	
	private BigDecimal precoVenda;
	private BigDecimal precoCusto;
	private BigDecimal estoqueAtual;
	
	
	
    public ProdutoDTO(Produto produto) {
		this.id = produto.getId();
		this.codigo = produto.getCodigo();
		this.descricao = produto.getDescricao();
		this.precoVenda = produto.getPrecoVenda();
		this.precoCusto = produto.getPrecoCusto();
		this.estoqueAtual = produto.getEstoqueAtual();
	}
    
    public static Page<ProdutoDTO> converter(Page<Produto> produtos) {
		return produtos.map(ProdutoDTO::new);
	}
}
