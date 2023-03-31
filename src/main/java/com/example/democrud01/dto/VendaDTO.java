package com.example.democrud01.dto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;

import com.example.democrud01.model.ItemVenda;
import com.example.democrud01.model.Venda;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VendaDTO {
	
	private Long id;
	private Long idUsuario;
	private String usuario;
	private Long idCliente;
	private String cliente;
	private Long idCaixa;
	private Calendar dataVenda;
	
	private PagamentoForm pagamento;
	
	public VendaDTO(Venda venda) {
		this.id = venda.getId();
		this.idUsuario = venda.getUserSistem().getId();
		this.usuario = venda.getUserSistem().getName();
		this.idCliente = venda.getCliente() !=null ? venda.getCliente().getId() : null;
		this.cliente = venda.getCliente() !=null ? venda.getCliente().getName(): "";
		this.dataVenda = venda.getDataVenda();
		this.idCaixa = venda.getCaixa().getId(); 
		pagamento = new PagamentoForm();
		pagamento.setCartaoCredito(venda.getCartaoCredito());
		pagamento.setCartaoDebito(venda.getCartaoDebito());
		pagamento.setDinheiro(venda.getDinheiro());
		pagamento.setSaldoDevedor(venda.getSaldoDevedor());
		pagamento.setTotal(venda.getTotal());
	}
	
	private List<ItemVendaDTO> convertItens(Collection<ItemVenda> itensVenda){
		List<ItemVendaDTO> retItens = new ArrayList<>();
		for (ItemVenda itemVenda : itensVenda) {
			retItens.add(new ItemVendaDTO(itemVenda));
		}
		return retItens;
	}
	
	public static Page<VendaDTO> converter(Page<Venda> vendas) {
		return vendas.map(VendaDTO::new);
	}
	


}
