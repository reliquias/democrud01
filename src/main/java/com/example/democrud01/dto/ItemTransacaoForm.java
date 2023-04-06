package com.example.democrud01.dto;

import java.math.BigDecimal;
import java.util.Optional;

import com.example.democrud01.model.ItemTransacao;
import com.example.democrud01.model.Produto;
import com.example.democrud01.model.Transacao;
import com.example.democrud01.service.ProdutoService;
import com.example.democrud01.service.TransacaoService;

import lombok.Getter;

@Getter
public class ItemTransacaoForm {
	
	
	private Short sequencia;
	private BigDecimal quantidade;
	private BigDecimal valorUnitario;
	private BigDecimal valorTotal;
    
    private Long idProduto;
    private Long idTransacao;

	public ItemTransacao converter(ProdutoService produtoService, TransacaoService transacaoService) {
		Optional<Produto> produto = produtoService.get(idProduto);
		Optional<Transacao> transacao = transacaoService.get(idTransacao);
    	return new ItemTransacao(sequencia, quantidade, valorUnitario, valorTotal, produto.get(), transacao.get());
    }
	
	public ItemTransacao converterSemTransacao(ProdutoService produtoService) {
		Optional<Produto> produto = produtoService.get(idProduto);
		return new ItemTransacao(sequencia, quantidade, valorUnitario, valorTotal, produto.get(), null);
    }
	


}
