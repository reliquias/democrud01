package com.example.democrud01.dto;

import java.math.BigDecimal;
import java.util.Calendar;

import org.springframework.data.domain.Page;

import com.example.democrud01.enums.TipoTransacao;
import com.example.democrud01.model.Transacao;
import com.example.democrud01.util.Utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExtratoDTO {
	
	private long id;
	private Calendar dataTransacao;
	private String descricao;
	private BigDecimal saldoCaixa;
	private BigDecimal dinheiro;
	private BigDecimal cartaoCredito;
	private BigDecimal cartaoDebito;
	private BigDecimal debitado;
	private BigDecimal totalTransacao;
	
	
	public ExtratoDTO(Transacao transacao) {
		this.id = transacao.getId();
		this.dataTransacao = transacao.getDataTransacao();
		this.descricao = transacao.getTipo().getDescricao();
		this.saldoCaixa = transacao.getTotal();
		this.dinheiro = transacao.getDinheiro();
		this.cartaoCredito = transacao.getCartaoCredito();
		this.cartaoDebito = transacao.getCartaoDebito();
		this.debitado = transacao.getTipo().compareTo(TipoTransacao.venda) == 0 ? Utils.substituiNuloPorZero(transacao.getSaldoDevedor()).compareTo(transacao.getTotal())!= 0 ? Utils.substituiNuloPorZero(transacao.getSaldoDevedor()) : null : null;
		this.totalTransacao = Utils.substituiNuloPorZero(this.cartaoCredito).add(Utils.substituiNuloPorZero(this.cartaoDebito).add(Utils.substituiNuloPorZero(this.dinheiro).add(Utils.substituiNuloPorZero(this.debitado))));
	}
	
	public static Page<ExtratoDTO> converter(Page<Transacao> transacaos) {
		return transacaos.map(ExtratoDTO::new);
	}
}
