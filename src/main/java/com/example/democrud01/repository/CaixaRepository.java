package com.example.democrud01.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.democrud01.model.Caixa;
import com.example.democrud01.model.UserSistem;

@Repository
public interface CaixaRepository extends JpaRepository<Caixa, Long> {

	Optional<Caixa> findByDataFechamentoIsNull();
	
	Optional<Caixa> findByUsuarioAberturaAndDataFechamentoIsNull(UserSistem usuarioAbertura);
	
}