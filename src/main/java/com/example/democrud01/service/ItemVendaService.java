package com.example.democrud01.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.democrud01.model.ItemVenda;
import com.example.democrud01.model.Venda;
import com.example.democrud01.repository.ItemVendaRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ItemVendaService {
	
	@Autowired
	private ItemVendaRepository itemVendaRepository;
	
	public ItemVenda create(ItemVenda itemVenda) {
		ItemVenda itemVendaCad = itemVendaRepository.save(itemVenda);
		return itemVendaCad;
	}

	public ResponseEntity<ItemVenda> update(long id, ItemVenda itemVenda) {
		log.info("ItemVendaService update ");
		
		ItemVenda record = itemVendaRepository.findById(id).get();
		if(record == null) {
			return ResponseEntity.notFound().build();
		}
		record.setProduto(itemVenda.getProduto());
		record.setQuantidade(itemVenda.getQuantidade());
		record.setSequencia(itemVenda.getSequencia());
		record.setValorTotal(itemVenda.getValorTotal());
		record.setValorUnitario(itemVenda.getValorUnitario());
		record.setVenda(itemVenda.getVenda());
		
		ItemVenda updated = itemVendaRepository.save(record);
		return ResponseEntity.ok().body(updated);
	}
		
	public void deleteById(long id) {
		itemVendaRepository.deleteById(id);
	}

	public Optional<ItemVenda> get(Long id) {
		log.info("Executando ItemVendaService metodo getItemVenda");
		return itemVendaRepository.findById(id);
	}

	public Page<ItemVenda> getAlByVenda(Venda venda, Pageable paginacao) {
		return itemVendaRepository.findByVenda(venda, paginacao);
	}
	
	public Page<ItemVenda> getAll(Pageable paginacao) {
		return itemVendaRepository.findAll(paginacao);
	}
	
	public List<ItemVenda> getAll() {
		return itemVendaRepository.findAll();
	}
	
	public Page<ItemVenda> findByVenda(Venda venda, Pageable paginacao) {
		return itemVendaRepository.findByVenda(venda, paginacao);
	}
}