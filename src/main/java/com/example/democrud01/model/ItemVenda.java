package com.example.democrud01.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ItemVenda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private BigDecimal quantidade;
	private BigDecimal preco;
	
	@ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
	
	@ManyToOne
    @JoinColumn(name = "venda_id")
    private Venda venda;

	public ItemVenda(BigDecimal quantidade, BigDecimal preco, Produto produto, Venda venda) {
		this.quantidade = quantidade;
		this.preco = preco;
		this.produto = produto;
		this.venda = venda;
	}
}
