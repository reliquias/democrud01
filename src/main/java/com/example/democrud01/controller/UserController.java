package com.example.democrud01.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.democrud01.model.UserSistem;
import com.example.democrud01.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({"/api/users"})
@Api(value = "Usuario do sistema")
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
public class UserController {
	
	@Autowired
    private UserService UserService;

	@ApiOperation(value = "Cadastrar um novo usuario do sistema")
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody UserSistem user) {
        UserService.create(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

	@ApiOperation(value = "Alterar usuario do sistema")
	@PutMapping(value="/{id}")
	public ResponseEntity update(@PathVariable("id") long id, @RequestBody UserSistem user) {
		return UserService.update(id, user);
	}

	@ApiOperation(value = "Procura um usuario pelo id")
    @GetMapping(path = "/{id}")
    public ResponseEntity get(@PathVariable final Long id) {
    	return UserService.get(id)
 	           .map(record -> ResponseEntity.ok().body(record))
 	           .orElse(ResponseEntity.notFound().build());
    }
    
	@ApiOperation(value = "Listas todos os usuarios do sistema")
    @GetMapping
    public List getAll() {
        return UserService.getAll();
    }
}
