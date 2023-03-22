package com.example.democrud01.dto;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;

import com.example.democrud01.model.ItemVenda;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemVendaDTO {
	
	private Long id;
	private Short sequencia;
	private BigDecimal quantidade;
	private BigDecimal valorUnitario;
	private BigDecimal valorTotal;
    
    private Long idProduto;
    private String produto;
    private Long idVenda;
    
    public static Page<ItemVendaDTO> converter(Page<ItemVenda> itens) {
		return itens.map(ItemVendaDTO::new);
	}

	public ItemVendaDTO(ItemVenda item) {
		this.id = item.getId();
		this.sequencia = item.getSequencia();
		this.quantidade = item.getQuantidade();
		this.valorUnitario = item.getValorUnitario();
		this.valorTotal = item.getValorTotal();
		this.idProduto = item.getProduto().getId();
		this.produto = item.getProduto().getCodigo();
		this.idVenda = item.getVenda().getId();
	}

}
