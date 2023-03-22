package com.example.democrud01.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.democrud01.model.ItemVenda;
import com.example.democrud01.model.Venda;

@Repository
public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {
	
	Page<ItemVenda> findByVenda(Venda venda, Pageable paginacao);
}