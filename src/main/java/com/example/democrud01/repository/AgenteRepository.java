package com.example.democrud01.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.democrud01.enums.TipoAgente;
import com.example.democrud01.model.Agente;
import com.example.democrud01.model.UserSistem;

@Repository
public interface AgenteRepository extends JpaRepository<Agente, Long> {
	
UserSistem findByEmail(@Param("email") String email);
	
	@Query("select c from Agente c  where UPPER(c.name) LIKE UPPER(CONCAT('%',?1,'%'))")
	Page<Agente> findByName(String name, Pageable paginacao);
	
	Page<Agente> findByTipo(TipoAgente tipo, Pageable paginacao);
	
}