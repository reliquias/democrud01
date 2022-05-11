package com.example.democrud01.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.democrud01.model.Cliente;
import com.example.democrud01.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente create(Cliente user) {
		return clienteRepository.save(user);
	}

	public ResponseEntity<Cliente> update(long id, Cliente item) {
		return clienteRepository.findById(id).map(record -> {
			record.setName(item.getName());
			record.setCodigo(item.getCodigo());
			record.setEmail(item.getEmail());
			record.setPhone(item.getPhone());
			
			Cliente updated = clienteRepository.save(record);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	public Optional<Cliente> get(Long id) {
		return clienteRepository.findById(id);
	}
	
	public void deleteById(Long id) {
		clienteRepository.deleteById(id);
	}

	public List<Cliente> getAll() {
		return clienteRepository.findAll();
	}
}