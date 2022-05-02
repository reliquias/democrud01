package com.example.democrud01.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.democrud01.model.Produto;
import com.example.democrud01.service.ProdutoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({"/api/produto"})
@Api(value = "Produto")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProdutoController {
	
	@Autowired
    private ProdutoService produtoService;

	@ApiOperation(value = "Cadastrar um novo produto do sistema")
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody Produto objeto) {
        produtoService.create(objeto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

	@ApiOperation(value = "Alterar produto do sistema")
	@PutMapping(value="/{id}")
	public ResponseEntity update(@PathVariable("id") long id, @RequestBody Produto objeto) {
		return produtoService.update(id, objeto);
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
    public List getAll() {
        return produtoService.getAll();
    }
}
