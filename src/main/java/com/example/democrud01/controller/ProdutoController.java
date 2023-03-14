package com.example.democrud01.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.democrud01.dto.ProdutoDTO;
import com.example.democrud01.dto.ProdutoForm;
import com.example.democrud01.model.Produto;
import com.example.democrud01.service.ProdutoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({"/api/produto"})
@Api(value = "Produto")
@CrossOrigin(origins = {"http://localhost:4200/", "http://www.505crm.com.br/"}, maxAge = 3600, allowCredentials = "true", allowedHeaders = "*")
public class ProdutoController {
	
	@Autowired
    private ProdutoService produtoService;

	@ApiOperation(value = "Cadastrar um novo produto do sistema")
    @PostMapping
    public ResponseEntity<ProdutoDTO> create(@RequestBody ProdutoForm form, UriComponentsBuilder uriBuilder) {
        Produto produto = form.converter();
		produtoService.create(produto);
		URI uri = uriBuilder.path("/api/produto/{id}").buildAndExpand(produto.getId()).toUri();
        return ResponseEntity.created(uri).body(new ProdutoDTO(produto));
    }

	@ApiOperation(value = "Alterar produto do sistema")
	@PutMapping(value="/{id}")
	public ResponseEntity<ProdutoDTO> update(@PathVariable("id") long id, @RequestBody ProdutoForm form, UriComponentsBuilder uriBuilder) {
		Produto produto = form.converter();
		produtoService.update(id, produto);
		URI uri = uriBuilder.path("/api/produto/{id}").buildAndExpand(produto.getId()).toUri();
        return ResponseEntity.created(uri).body(new ProdutoDTO(produto));
	}

	@ApiOperation(value = "Procura um produto pelo id")
    @GetMapping(path = "/{id}")
    public ResponseEntity get(@PathVariable final Long id) {
    	return produtoService.get(id)
 	           .map(record -> ResponseEntity.ok().body(record))
 	           .orElse(ResponseEntity.notFound().build());
    }
	
	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity <?> delete(@PathVariable long id) {
	   return produtoService.get(id)
	           .map(record -> {
	        	   produtoService.deleteById(id);
	               return ResponseEntity.ok().build();
	           }).orElse(ResponseEntity.notFound().build());
	}
    
	@ApiOperation(value = "Listas todos os produtos do sistema")
    @GetMapping
    public Page<ProdutoDTO> getAll(@RequestParam int pagina, @RequestParam int qtd, @RequestParam String ordenacao, @RequestParam Boolean ordemAsc,@RequestParam(required=false) String codigo) {
		Pageable page = PageRequest.of(pagina, qtd, ordemAsc ? Direction.ASC : Direction.DESC, ordenacao);
		
		Page<Produto> produtos = codigo !=null ? produtoService.getAllByCodigo(codigo, page) : produtoService.getAll(page);
        return ProdutoDTO.converter(produtos);
    }
}
