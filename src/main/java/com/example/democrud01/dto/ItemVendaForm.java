package com.example.democrud01.dto;

import java.math.BigDecimal;
import java.util.Optional;

import com.example.democrud01.model.ItemVenda;
import com.example.democrud01.model.Produto;
import com.example.democrud01.model.Venda;
import com.example.democrud01.service.ProdutoService;
import com.example.democrud01.service.VendaService;
import com.sun.istack.NotNull;

import lombok.Getter;

@Getter
public class ItemVendaForm {
	
	@NotNull
	private Long idVenda;
	@NotNull
	private Long idProduto;
	
	private BigDecimal quantidade;
	private BigDecimal preco;

    public ItemVenda converter(VendaService vendaService, ProdutoService produtoService) {
    	Optional<Venda> venda = vendaService.get(idVenda);
    	Optional<Produto> produto = produtoService.get(idProduto);
        return new ItemVenda(quantidade, preco, produto.get(), venda.get());
    }

}
