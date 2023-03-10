package com.example.democrud01.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.democrud01.model.SelectUsuario;
import com.example.democrud01.repository.SelectUsuarioRepository;

@Service
public class SelectUsuarioService {

	@Autowired
	private SelectUsuarioRepository selectUsuarioRepository;
	
	public SelectUsuario create(SelectUsuario user) {
		return selectUsuarioRepository.save(user);
	}

	public ResponseEntity<SelectUsuario> update(long id, SelectUsuario item) {
		return selectUsuarioRepository.findById(id).map(record -> {
			record.setApelido(item.getApelido());
			record.setArquivo(item.getArquivo());
			record.setDesativado(item.getDesativado());
			record.setFormatoPagina(item.getFormatoPagina());
			record.setNomeArquivo(item.getNomeArquivo());
			record.setSelectUsu(item.getSelectUsu());
			record.setTitulo(item.getTitulo());
			
			SelectUsuario updated = selectUsuarioRepository.save(record);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	public Optional<SelectUsuario> get(Long id) {
		return selectUsuarioRepository.findById(id);
	}
	
	public void deleteById(Long id) {
		selectUsuarioRepository.deleteById(id);
	}

	public Page<SelectUsuario> getAll(Pageable paginacao) {
		return selectUsuarioRepository.findAll(paginacao);
	}	
}