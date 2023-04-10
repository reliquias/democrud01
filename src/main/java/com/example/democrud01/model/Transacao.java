package com.example.democrud01.model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.democrud01.enums.TipoTransacao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "FW23_TRANSACAO")
public class Transacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "USUARIO_ID", referencedColumnName = "ID")
	private UserSistem userSistem;

	@ManyToOne
	@JoinColumn(name = "CLIENTE_ID", referencedColumnName = "ID")
    private Agente cliente;
	
	private BigDecimal total;
	private BigDecimal saldoDevedor;
	private BigDecimal dinheiro;
	private BigDecimal cartaoCredito;
	private BigDecimal cartaoDebito;
	private Calendar dataTransacao = Calendar.getInstance();
	
	private TipoTransacao tipo;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "transacao")
	private Collection<ItemTransacao> itensTransacaoCollection;
	
	@JoinColumn(name = "CAIXA_ID", referencedColumnName = "ID")
    @ManyToOne
    private Caixa caixa;

	public Transacao(UserSistem userSistem, Agente cliente) {
		this.userSistem = userSistem;
		this.cliente = cliente;
	}

	public Transacao(UserSistem userSistem, Agente cliente, BigDecimal saldoDevedor, BigDecimal dinheiro,
			BigDecimal cartaoCredito, BigDecimal cartaoDebito, BigDecimal total, Caixa caixa, TipoTransacao tipo) {
		this.userSistem = userSistem;
		this.cliente = cliente;
		this.saldoDevedor = saldoDevedor;
		this.dinheiro = dinheiro;
		this.cartaoCredito = cartaoCredito;
		this.cartaoDebito = cartaoDebito;
		this.total = total;
		this.caixa = caixa;
		this.tipo = tipo;
	}

	public Transacao(BigDecimal dinheiro, BigDecimal cartaoCredito, BigDecimal cartaoDebito) {
		this.dinheiro = dinheiro;
		this.cartaoCredito = cartaoCredito;
		this.cartaoDebito = cartaoDebito;
	}
	
	
}
