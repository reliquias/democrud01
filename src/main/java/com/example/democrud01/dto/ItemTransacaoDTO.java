package com.example.democrud01.dto;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;

import com.example.democrud01.model.ItemTransacao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemTransacaoDTO {
	
	private Long id;
	private Short sequencia;
	private BigDecimal quantidade;
	private BigDecimal valorUnitario;
	private BigDecimal valorTotal;
    
    private Long idProduto;
    private String produto;
    private Long idTransacao;
    
    public static Page<ItemTransacaoDTO> converter(Page<ItemTransacao> itens) {
		return itens.map(ItemTransacaoDTO::new);
	}

	public ItemTransacaoDTO(ItemTransacao item) {
		this.id = item.getId();
		this.sequencia = item.getSequencia();
		this.quantidade = item.getQuantidade();
		this.valorUnitario = item.getValorUnitario();
		this.valorTotal = item.getValorTotal();
		this.idProduto = item.getProduto().getId();
		this.produto = item.getProduto().getCodigo();
		this.idTransacao = item.getTransacao().getId();
	}

}
