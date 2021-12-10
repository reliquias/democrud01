package com.example.democrud01.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.democrud01.model.UserSistem;
import com.example.democrud01.service.UserService;

@RestController
@RequestMapping({"/api/users"})
public class UserController {
	
	@Autowired
    private UserService UserService;

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody UserSistem user) {
        UserService.create(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity get(@PathVariable final Long id) {
    	return UserService.get(id)
 	           .map(record -> ResponseEntity.ok().body(record))
 	           .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping
    public List getAll() {
        return UserService.getAll();
    }
}
