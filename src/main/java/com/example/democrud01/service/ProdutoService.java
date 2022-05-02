package com.example.democrud01.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
			record.setNome(item.getNome());
			record.setCodigo(item.getCodigo());
			record.setDescricao(item.getDescricao());
			record.setPreco(item.getPreco());
			record.setQuantidade(item.getQuantidade());
			
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

	public List<Produto> getAll() {
		// TODO Auto-generated method stub
		return produtoRepository.findAll();
	}
}