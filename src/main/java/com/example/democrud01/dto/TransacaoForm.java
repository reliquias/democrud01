package com.example.democrud01.dto;

import java.util.List;
import java.util.Optional;

import com.example.democrud01.enums.TipoTransacao;
import com.example.democrud01.model.Agente;
import com.example.democrud01.model.Caixa;
import com.example.democrud01.model.UserSistem;
import com.example.democrud01.model.Transacao;
import com.example.democrud01.service.AgenteService;
import com.example.democrud01.service.CaixaService;
import com.example.democrud01.service.UserService;

import lombok.Getter;

@Getter
public class TransacaoForm {
	
	
	private Long idUsuario;
	private Long idCliente;
	private Long idCaixa;
	private TipoTransacao tipo;
	
	private PagamentoForm pagamento;
	
	private List<ItemTransacaoForm> itens;

	public Transacao converter(UserService userService, AgenteService agenteService) {
		Optional<UserSistem> usuario = userService.get(idUsuario);
		Optional<Agente> cliente = agenteService.get(idCliente);
    	return new Transacao(usuario.get(), cliente.get());
    }
	
	public Transacao converterFull(UserService userService, AgenteService agenteService, CaixaService caixaService) {
		Optional<UserSistem> usuario = userService.get(idUsuario);
		Optional<Agente> cliente =  agenteService.get(idCliente != null ? idCliente : 0);
		Optional<Caixa> caixa =  caixaService.get(idCaixa != null ? idCaixa : 0);
		
		return new Transacao(usuario.get(), cliente.isEmpty() ? null : cliente.get(), pagamento.getSaldoDevedor(), pagamento.getDinheiro(), pagamento.getCartaoCredito(), pagamento.getCartaoDebito(), pagamento.getTotal(), caixa.get(), tipo);
    }
	
	

}
