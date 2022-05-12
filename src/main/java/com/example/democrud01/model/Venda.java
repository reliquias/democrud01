package com.example.democrud01.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Venda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String numero;
	private LocalDateTime dataVenda = LocalDateTime.now();
	
	@ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

	public Venda(String numero, Cliente cliente) {
		this.numero = numero;
		this.cliente = cliente;
	}
}
