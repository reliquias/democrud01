package com.example.democrud01.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.democrud01.model.Caixa;
import com.example.democrud01.model.Venda;
import com.example.democrud01.repository.CaixaRepository;
import com.example.democrud01.repository.VendaRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VendaService {
	
	@Autowired
	private VendaRepository vendaRepository;

	@Autowired
	private CaixaRepository caixaRepository;
	
	public Venda create(Venda venda) {
		Venda vendaCad = vendaRepository.save(venda);
		return vendaCad;
	}

	public ResponseEntity<Venda> update(long id, Venda venda) {
		log.info("VendaService update ");
		
		Venda record = vendaRepository.findById(id).get();
		if(record == null) {
			return ResponseEntity.notFound().build();
		}
		record.setUserSistem(venda.getUserSistem());
		record.setCliente(venda.getCliente());
		
		Venda updated = vendaRepository.save(record);
		return ResponseEntity.ok().body(updated);
	}
		
	public void deleteById(long id) {
		vendaRepository.deleteById(id);
	}

	public Optional<Venda> get(Long id) {
		log.info("Executando VendaService metodo getVenda");
		return vendaRepository.findById(id);
	}

	public Page<Venda> getAll(Pageable paginacao) {
		return vendaRepository.findAll(paginacao);
	}
	
	public List<Venda> getAll() {
		return vendaRepository.findAll();
	}
	
	public Page<Venda> getAlByCaixa(Long idCaixa, Pageable paginacao) {
		Caixa caixa = caixaRepository.getById(idCaixa);
		return vendaRepository.findByCaixa(caixa, paginacao);
	}
}