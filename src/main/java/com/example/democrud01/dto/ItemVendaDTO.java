package com.example.democrud01.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
	private Long idProduto;
	private Long idVenda;
	private BigDecimal preco;
	private BigDecimal quantidade;
	
	

	public ItemVendaDTO(ItemVenda item) {
		this.id = item.getId();
		this.idProduto = item.getProduto().getId();
		this.idVenda = item.getVenda().getId();
		this.preco = item.getPreco();
		this.quantidade = item.getQuantidade();
	}

	public static Page<ItemVendaDTO> converter(Page<ItemVenda> vendas) {
		return vendas.map(ItemVendaDTO::new);
	}
}
