package com.example.democrud01.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.democrud01.enums.TipoTransacao;
import com.example.democrud01.model.Caixa;
import com.example.democrud01.model.Transacao;
import com.example.democrud01.model.UserSistem;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
	
	Page<Transacao> findByUserSistem(UserSistem userSistem, Pageable paginacao);

	Page<Transacao> findByCaixa(Caixa caixa, Pageable paginacao);
	
	List<Transacao> findByCaixa(Caixa caixa);
	
	Page<Transacao> findAllByTipoAndCaixa(TipoTransacao tipo, Caixa caixa, Pageable paginacao);
	
	List<Transacao> findAllByCaixa(Caixa caixa);
}