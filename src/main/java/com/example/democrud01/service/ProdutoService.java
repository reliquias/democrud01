package com.example.democrud01.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.democrud01.model.Produto;
import com.example.democrud01.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	public Produto create(Produto user) {
		return produtoRepository.save(user);
	}

	public ResponseEntity<Produto> update(long id, Produto item) {
		return produtoRepository.findById(id).map(record -> {
			record.setCodigo(item.getCodigo());
			record.setDescricao(item.getDescricao());
			record.setEstoqueAtual(item.getEstoqueAtual());
			record.setPrecoCusto(item.getPrecoCusto());
			record.setPrecoVenda(item.getPrecoVenda());
			
			Produto updated = produtoRepository.save(record);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	public Optional<Produto> get(Long id) {
		return produtoRepository.findById(id);
	}
	
	public void deleteById(Long id) {
		produtoRepository.deleteById(id);
	}

	public Page<Produto> getAll(Pageable paginacao) {
		return produtoRepository.findAll(paginacao);
	}
	
	public Page<Produto> getAllByCodigo(String codigo, Pageable paginacao) {
		return produtoRepository.findByNome(codigo, paginacao);
	}
	
	public ResponseEntity<Produto> updateSaldo(Long id, Produto produto, BigDecimal qtdVendida) {
		produto.setEstoqueAtual(produto.getEstoqueAtual().subtract(qtdVendida));
 	   	return update(id, produto);
	}
}