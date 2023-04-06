package com.example.democrud01.dto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;

import com.example.democrud01.enums.TipoTransacao;
import com.example.democrud01.model.ItemTransacao;
import com.example.democrud01.model.Transacao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoDTO {
	
	private Long id;
	private Long idUsuario;
	private String usuario;
	private Long idCliente;
	private String cliente;
	private Long idCaixa;
	private Calendar dataTransacao;
	private TipoTransacao tipo;
	private PagamentoForm pagamento;
	
	
	public TransacaoDTO(Transacao transacao) {
		this.id = transacao.getId();
		this.idUsuario = transacao.getUserSistem().getId();
		this.usuario = transacao.getUserSistem().getName();
		this.idCliente = transacao.getCliente() !=null ? transacao.getCliente().getId() : null;
		this.cliente = transacao.getCliente() !=null ? transacao.getCliente().getName(): "";
		this.dataTransacao = transacao.getDataTransacao();
		this.idCaixa = transacao.getCaixa().getId(); 
		this.tipo = transacao.getTipo();
		pagamento = new PagamentoForm();
		pagamento.setCartaoCredito(transacao.getCartaoCredito());
		pagamento.setCartaoDebito(transacao.getCartaoDebito());
		pagamento.setDinheiro(transacao.getDinheiro());
		pagamento.setSaldoDevedor(transacao.getSaldoDevedor());
		pagamento.setTotal(transacao.getTotal());
	}
	
	private List<ItemTransacaoDTO> convertItens(Collection<ItemTransacao> itensTransacao){
		List<ItemTransacaoDTO> retItens = new ArrayList<>();
		for (ItemTransacao itemTransacao : itensTransacao) {
			retItens.add(new ItemTransacaoDTO(itemTransacao));
		}
		return retItens;
	}
	
	public static Page<TransacaoDTO> converter(Page<Transacao> transacaos) {
		return transacaos.map(TransacaoDTO::new);
	}
	


}
