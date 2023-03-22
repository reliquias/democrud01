package com.example.democrud01.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PagamentoForm {
	
	
	private BigDecimal total;
	private BigDecimal saldoDevedor;
	private BigDecimal dinheiro;
	private BigDecimal cartaoCredito;
	private BigDecimal cartaoDebito;
}
