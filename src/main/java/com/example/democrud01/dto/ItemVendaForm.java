package com.example.democrud01.dto;

import java.math.BigDecimal;
import java.util.Optional;

import com.example.democrud01.model.ItemVenda;
import com.example.democrud01.model.Produto;
import com.example.democrud01.model.Venda;
import com.example.democrud01.service.ProdutoService;
import com.example.democrud01.service.VendaService;

import lombok.Getter;

@Getter
public class ItemVendaForm {
	
	
	private Short sequencia;
	private BigDecimal quantidade;
	private BigDecimal valorUnitario;
	private BigDecimal valorTotal;
    
    private Long idProduto;
    private Long idVenda;

	public ItemVenda converter(ProdutoService produtoService, VendaService vendaService) {
		Optional<Produto> produto = produtoService.get(idProduto);
		Optional<Venda> venda = vendaService.get(idVenda);
    	return new ItemVenda(sequencia, quantidade, valorUnitario, valorTotal, produto.get(), venda.get());
    }
	
	public ItemVenda converterSemVenda(ProdutoService produtoService) {
		Optional<Produto> produto = produtoService.get(idProduto);
		return new ItemVenda(sequencia, quantidade, valorUnitario, valorTotal, produto.get(), null);
    }
	


}
