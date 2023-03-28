package com.example.democrud01.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.democrud01.enums.TipoAgente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "FW23_AGENTE")
public class Agente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	private TipoAgente tipo;
	private String name;
	private String email;
	private String phone;
	private Boolean desativado;
	private BigDecimal credito;
	
	public Agente(TipoAgente tipo, String name, String email, String phone, Boolean desativado, BigDecimal credito) {
		this.tipo = tipo;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.desativado = desativado;
		this.credito = this.credito;
	}
	
	
}
