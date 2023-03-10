package com.example.democrud01.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.democrud01.model.SelectUsuario;
import com.example.democrud01.model.SelectUsuarioFiltro;
import com.example.democrud01.repository.SelectUsuarioFiltroRepository;
import com.example.democrud01.repository.SelectUsuarioRepository;

@Service
public class SelectUsuarioFiltroService {

	@Autowired
	private SelectUsuarioFiltroRepository selectUsuarioFiltroRepository;
	

	@Autowired
	private SelectUsuarioRepository selectUsuarioRepository;
	
	public SelectUsuarioFiltro create(SelectUsuarioFiltro user) {
		return selectUsuarioFiltroRepository.save(user);
	}

	public ResponseEntity<SelectUsuarioFiltro> update(long id, SelectUsuarioFiltro item) {
		return selectUsuarioFiltroRepository.findById(id).map(record -> {
			record.setLabel(item.getLabel());
			record.setSelectFiltro(item.getSelectFiltro());
			record.setSelectUsuario(item.getSelectUsuario());
			
			SelectUsuarioFiltro updated = selectUsuarioFiltroRepository.save(record);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	public Optional<SelectUsuarioFiltro> get(Long id) {
		return selectUsuarioFiltroRepository.findById(id);
	}
	
	public void deleteById(Long id) {
		selectUsuarioFiltroRepository.deleteById(id);
	}

	public Page<SelectUsuarioFiltro> getAll(Pageable paginacao) {
		return selectUsuarioFiltroRepository.findAll(paginacao);
	}	
	
	public Page<SelectUsuarioFiltro> findBySelectUsuario(Long idRelatorio, Pageable paginacao) {
		Optional<SelectUsuario> select = selectUsuarioRepository.findById(idRelatorio);
		return selectUsuarioFiltroRepository.findBySelectUsuario(select.get(), paginacao);
	}
}