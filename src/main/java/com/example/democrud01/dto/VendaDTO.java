package com.example.democrud01.dto;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

import com.example.democrud01.model.Venda;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    
    public static Page<VendaDTO> converter(Page<Venda> vendas) {
		return vendas.map(VendaDTO::new);
	}
    
    

}
