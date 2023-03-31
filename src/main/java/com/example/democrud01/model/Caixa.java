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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "FW23_CAIXA")
public class Caixa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "USUARIO_ABERTURA_ID", referencedColumnName = "ID")
	private UserSistem usuarioAbertura;
	
	private Calendar dataAbertura;
	
	@ManyToOne
	@JoinColumn(name = "USUARIO_FECHAMENTO_ID", referencedColumnName = "ID")
	private UserSistem usuarioFechamento;

	private Calendar dataFechamento;
	
	private BigDecimal valorTotal;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "caixa")
	private Collection<Venda> vendasCollection;

	public Caixa(UserSistem usuarioAbertura, Calendar dataAbertura, UserSistem usuarioFechamento,
			Calendar dataFechamento, BigDecimal valorTotal) {
		this.usuarioAbertura = usuarioAbertura;
		this.dataAbertura = dataAbertura;
		this.usuarioFechamento = usuarioFechamento;
		this.dataFechamento = dataFechamento;
		this.valorTotal = valorTotal;
	}
	
	
}
