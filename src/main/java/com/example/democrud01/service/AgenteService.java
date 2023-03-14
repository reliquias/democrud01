package com.example.democrud01.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.democrud01.enums.TipoAgente;
import com.example.democrud01.model.Agente;
import com.example.democrud01.repository.AgenteRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AgenteService {
	
	@Autowired
	private AgenteRepository agenteRepository;
	
	public Agente create(Agente agente) {
		Agente agenteCad = agenteRepository.save(agente);
		return agenteCad;
	}

	public ResponseEntity<Agente> update(long id, Agente agente) {
		log.info("AgenteService update " + agente.getEmail());
		
		Agente record = agenteRepository.findById(id).get();
		if(record == null) {
			return ResponseEntity.notFound().build();
		}
		record.setName(agente.getName());
		record.setEmail(agente.getEmail());
		record.setPhone(agente.getPhone());
		record.setTipo(agente.getTipo());
		
		Agente updated = agenteRepository.save(record);
		return ResponseEntity.ok().body(updated);
	}
		
	public void deleteById(long id) {
		agenteRepository.deleteById(id);
	}

	public Optional<Agente> get(Long id) {
		log.info("Executando AgenteService metodo getAgente");
		return agenteRepository.findById(id);
	}

	public Page<Agente> getAll(Pageable paginacao) {
		return agenteRepository.findAll(paginacao);
	}
	
	public Page<Agente> getAllByName(String name, Pageable paginacao) {
		return agenteRepository.findByName(name, paginacao);
	}

	public Page<Agente> findByTipo(TipoAgente tipo, Pageable paginacao) {
		return agenteRepository.findByTipo(tipo, paginacao);
	}

	public List<Agente> getAll() {
		return agenteRepository.findAll();
	}
}