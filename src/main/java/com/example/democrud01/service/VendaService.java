package com.example.democrud01.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.democrud01.model.Venda;
import com.example.democrud01.repository.VendaRepository;

@Service
public class VendaService {

	@Autowired
	private VendaRepository vendaRepository;

	public Venda create(Venda user) {
		return vendaRepository.save(user);
	}

	public ResponseEntity<Venda> update(long id, Venda item) {
		return vendaRepository.findById(id).map(record -> {
			record.setNumero(item.getNumero());
			record.setDataVenda(item.getDataVenda());
			record.setCliente(item.getCliente());
			
			Venda updated = vendaRepository.save(record);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	public Optional<Venda> get(Long id) {
		return vendaRepository.findById(id);
	}
	
	public void deleteById(Long id) {
		vendaRepository.deleteById(id);
	}

	public List<Venda> getAll() {
		return vendaRepository.findAll();
	}
}