package com.example.democrud01.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.democrud01.model.ItemVenda;
import com.example.democrud01.repository.ItemVendaRepository;

@Service
public class ItemVendaService {

	@Autowired
	private ItemVendaRepository itemVendaRepository;

	public ItemVenda create(ItemVenda user) {
		return itemVendaRepository.save(user);
	}

	public ResponseEntity<ItemVenda> update(long id, ItemVenda item) {
		return itemVendaRepository.findById(id).map(record -> {
			record.setPreco(item.getPreco());
			record.setProduto(item.getProduto());
			record.setVenda(item.getVenda());
			record.setQuantidade(item.getQuantidade());
			
			ItemVenda updated = itemVendaRepository.save(record);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	public Optional<ItemVenda> get(Long id) {
		return itemVendaRepository.findById(id);
	}
	
	public void deleteById(Long id) {
		itemVendaRepository.deleteById(id);
	}

	public Page<ItemVenda> getAll(Pageable paginacao) {
		return itemVendaRepository.findAll(paginacao);
	}
}