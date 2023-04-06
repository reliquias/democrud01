package com.example.democrud01.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "FW23_ITEM_TRANSACAO")
public class ItemTransacao{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Short sequencia;
	
	private BigDecimal quantidade;
	
	private BigDecimal valorUnitario;
    
	private BigDecimal valorTotal;
    
	@JoinColumn(name = "PRODUTO_ID", referencedColumnName = "ID", nullable=false)
    @ManyToOne
    private Produto produto;
    
	@JoinColumn(name = "TRANSACAO_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne
    private Transacao transacao;

	public ItemTransacao(Short sequencia, BigDecimal quantidade, BigDecimal valorUnitario, BigDecimal valorTotal,
			Produto produto, Transacao transacao) {
		this.sequencia = sequencia;
		this.quantidade = quantidade;
		this.valorUnitario = valorUnitario;
		this.valorTotal = valorTotal;
		this.produto = produto;
		this.transacao = transacao;
	}
	
	
	
	
}
