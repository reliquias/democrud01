package com.example.democrud01.repository;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.democrud01.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	@Query("select c from Produto c  where UPPER(c.codigo) LIKE UPPER(CONCAT('%',?1,'%'))")
	Page<Produto> findByNome(String codigo, Pageable paginacao);

	@Modifying
	@Query("update Produto c set c.estoqueAtual = (c.estoqueAtual-?1) where c.id = ?2")
	void updateSaldo(BigDecimal qtdVendida, Long id);

}