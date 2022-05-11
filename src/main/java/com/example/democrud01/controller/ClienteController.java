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

import com.example.democrud01.model.Cliente;
import com.example.democrud01.service.ClienteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({"/api/cliente"})
@Api(value = "Cliente")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ClienteController {
	
	@Autowired
    private ClienteService clienteService;

	@ApiOperation(value = "Cadastrar um novo cliente do sistema")
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody Cliente objeto) {
        clienteService.create(objeto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

	@ApiOperation(value = "Alterar cliente do sistema")
	@PutMapping(value="/{id}")
	public ResponseEntity update(@PathVariable("id") long id, @RequestBody Cliente objeto) {
		return clienteService.update(id, objeto);
	}

	@ApiOperation(value = "Procura um cliente pelo id")
    @GetMapping(path = "/{id}")
    public ResponseEntity get(@PathVariable final Long id) {
    	return clienteService.get(id)
 	           .map(record -> ResponseEntity.ok().body(record))
 	           .orElse(ResponseEntity.notFound().build());
    }
	
	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity <?> delete(@PathVariable long id) {
	   return clienteService.get(id)
	           .map(record -> {
	        	   clienteService.deleteById(id);
	               return ResponseEntity.ok().build();
	           }).orElse(ResponseEntity.notFound().build());
	}
    
	@ApiOperation(value = "Listas todos os clientes do sistema")
    @GetMapping
    public List getAll() {
        return clienteService.getAll();
    }
}
