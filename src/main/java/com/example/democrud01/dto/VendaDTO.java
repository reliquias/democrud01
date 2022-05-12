package com.example.democrud01.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.democrud01.model.Venda;

import lombok.Getter;

@Getter
public class VendaDTO {

	private Long id;
	private String numero;
	private LocalDateTime dataVenda;
    private Long idCliente;
	
    public VendaDTO(Venda venda) {
		this.id = venda.getId();
		this.numero = venda.getNumero();
		this.dataVenda = venda.getDataVenda();
		this.idCliente = venda.getCliente().getId();
	}
    
    public static List<VendaDTO> converter(List<Venda> vendas) {
		return vendas.stream().map(VendaDTO::new).collect(Collectors.toList());
	}
    
    

}
