package com.example.democrud01.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.democrud01.model.SelectUsuario;
import com.example.democrud01.model.SelectUsuarioFiltro;

@Repository
public interface SelectUsuarioFiltroRepository extends JpaRepository<SelectUsuarioFiltro, Long> {
	
	List<SelectUsuarioFiltro> findBySelectUsuario(SelectUsuario selectUsuario);
	
	Page<SelectUsuarioFiltro> findBySelectUsuario(SelectUsuario selectUsuario, Pageable paginacao);

	
}