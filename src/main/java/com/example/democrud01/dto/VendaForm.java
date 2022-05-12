package com.example.democrud01.dto;

import java.util.Optional;

import com.example.democrud01.model.Cliente;
import com.example.democrud01.model.Venda;
import com.example.democrud01.service.ClienteService;
import com.sun.istack.NotNull;

import lombok.Getter;

@Getter
public class VendaForm {

	@NotNull
	private String numero;
	@NotNull
	private Long idCliente;

    public Venda converter(ClienteService clienteService) {
    	Optional<Cliente> cliente = clienteService.get(idCliente);
        return new Venda(numero, cliente.get());
    }
}
