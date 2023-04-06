package com.example.democrud01.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.democrud01.model.ItemTransacao;
import com.example.democrud01.model.Transacao;
import com.example.democrud01.repository.ItemTransacaoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ItemTransacaoService {
	
	@Autowired
	private ItemTransacaoRepository itemTransacaoRepository;
	
	public ItemTransacao create(ItemTransacao itemTransacao) {
		ItemTransacao itemTransacaoCad = itemTransacaoRepository.save(itemTransacao);
		return itemTransacaoCad;
	}

	public ResponseEntity<ItemTransacao> update(long id, ItemTransacao itemTransacao) {
		log.info("ItemTransacaoService update ");
		
		ItemTransacao record = itemTransacaoRepository.findById(id).get();
		if(record == null) {
			return ResponseEntity.notFound().build();
		}
		record.setProduto(itemTransacao.getProduto());
		record.setQuantidade(itemTransacao.getQuantidade());
		record.setSequencia(itemTransacao.getSequencia());
		record.setValorTotal(itemTransacao.getValorTotal());
		record.setValorUnitario(itemTransacao.getValorUnitario());
		record.setTransacao(itemTransacao.getTransacao());
		
		ItemTransacao updated = itemTransacaoRepository.save(record);
		return ResponseEntity.ok().body(updated);
	}
		
	public void deleteById(long id) {
		itemTransacaoRepository.deleteById(id);
	}

	public Optional<ItemTransacao> get(Long id) {
		log.info("Executando ItemTransacaoService metodo getItemTransacao");
		return itemTransacaoRepository.findById(id);
	}

	public Page<ItemTransacao> getAlByTransacao(Transacao transacao, Pageable paginacao) {
		return itemTransacaoRepository.findByTransacao(transacao, paginacao);
	}
	
	public Page<ItemTransacao> getAll(Pageable paginacao) {
		return itemTransacaoRepository.findAll(paginacao);
	}
	
	public List<ItemTransacao> getAll() {
		return itemTransacaoRepository.findAll();
	}
	
	public Page<ItemTransacao> findByTransacao(Transacao transacao, Pageable paginacao) {
		return itemTransacaoRepository.findByTransacao(transacao, paginacao);
	}
}