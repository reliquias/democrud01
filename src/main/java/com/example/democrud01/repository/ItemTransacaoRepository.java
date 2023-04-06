package com.example.democrud01.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.democrud01.model.ItemTransacao;
import com.example.democrud01.model.Transacao;

@Repository
public interface ItemTransacaoRepository extends JpaRepository<ItemTransacao, Long> {
	
	Page<ItemTransacao> findByTransacao(Transacao transacao, Pageable paginacao);
}