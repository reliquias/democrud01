package com.example.democrud01.dto;

import java.math.BigDecimal;
import java.util.Calendar;

import org.springframework.data.domain.Page;

import com.example.democrud01.model.Caixa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CaixaDTO {
	
	private Long id;
	private Long idUsuarioAbertura;
	private String nomeUsuarioAbertura;
	private Long idUsuarioFechamento;
	private String nomeUsuarioFechamento;
	private Calendar dataAbertura;
	private Calendar dataFechamento;
	private BigDecimal valorTotal;
	
	public CaixaDTO(Caixa caixa) {
		this.id = caixa.getId();
		this.idUsuarioAbertura = caixa.getUsuarioAbertura().getId();
		this.idUsuarioFechamento= caixa.getUsuarioFechamento()!=null ? caixa.getUsuarioFechamento().getId():null;
		this.nomeUsuarioAbertura = caixa.getUsuarioAbertura().getName();
		this.nomeUsuarioFechamento= caixa.getUsuarioFechamento()!=null ? caixa.getUsuarioFechamento().getName() : "";
		this.dataAbertura = caixa.getDataAbertura();
		this.dataFechamento = caixa.getDataFechamento();
		this.valorTotal = caixa.getValorTotal();
	}
	
	public static Page<CaixaDTO> converter(Page<Caixa> caixas) {
		return caixas.map(CaixaDTO::new);
	}
	


}
