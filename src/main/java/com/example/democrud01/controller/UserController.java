package com.example.democrud01.controller;

import java.net.URI;
import java.util.List;

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

import com.example.democrud01.dto.UserDTO;
import com.example.democrud01.dto.UserForm;
import com.example.democrud01.enums.RoleUser;
import com.example.democrud01.model.EmailMessage;
import com.example.democrud01.model.UserSistem;
import com.example.democrud01.service.MailSenderService;
import com.example.democrud01.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({"/api/users"})
@Api(value = "Usuario do sistema")
@CrossOrigin(origins = {"http://localhost:4200/", "http://www.505crm.com.br/"}, maxAge = 3600, allowCredentials = "true", allowedHeaders = "*")
public class UserController {
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private MailSenderService mailService;

	@ApiOperation(value = "Cadastrar um novo usuario do sistema")
	@PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserForm form, UriComponentsBuilder uriBuilder) {
        UserSistem user = form.converter();
        userService.create(user);
		URI uri = uriBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserDTO(user));
    }

	@ApiOperation(value = "Alterar usuario do sistema")
	@PutMapping(value="/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable("id") long id, @RequestBody UserForm form, UriComponentsBuilder uriBuilder) {
		UserSistem user = form.converter();
		userService.update(id, user);
		URI uri = uriBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserDTO(user));
	}

	@ApiOperation(value = "Alterar senha do usuario")
	@PutMapping(value="/updatePassword")
	public void updatePassword(@RequestParam long id, @RequestParam String password, UriComponentsBuilder uriBuilder) {
		userService.updatePassword(id, password);
	}

	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity <?> delete(@PathVariable long id) {
	   return userService.get(id)
	           .map(record -> {
	        	   userService.deleteById(id);
	               return ResponseEntity.ok().build();
	           }).orElse(ResponseEntity.notFound().build());
	}

	@ApiOperation(value = "Procura um usuario pelo id")
    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDTO> get(@PathVariable final Long id) {
		UserSistem user = userService.get(id).get();
    	return ResponseEntity.ok().body(new UserDTO(user));
    }

	@ApiOperation(value = "Procura um usuario pelo email")
	@PutMapping(path = "/forgetPassword")
	public void forgetPassword(@RequestParam final String email) {
		UserSistem user = userService.getByEmail(email);
		String password = UserService.initialPassword();
		
		userService.updatePassword(user.getId(), password);
		mailService.sender(
				user.getEmail(), 
				EmailMessage.createTitleUpdatePassword(user), 
				EmailMessage.messageToUpdatePassword(user, password));
	}
    
	@ApiOperation(value = "Verifica se o Password está correto")
    @GetMapping(path = "/isCorrectPassword")
    public boolean isCorrectPassword(@RequestParam long id, @RequestParam String password) {
		return userService.isCorrectPassword(id, password);
    }

	@ApiOperation(value = "Listas todos os usuarios do sistema")
	@GetMapping
	public Page<UserDTO> getAll(@RequestParam int pagina, @RequestParam int qtd, @RequestParam String ordenacao, @RequestParam Boolean ordemAsc,@RequestParam(required=false) String name) {
		Pageable page = PageRequest.of(pagina, qtd, ordemAsc ? Direction.ASC : Direction.DESC, ordenacao);
		
		Page<UserSistem> users = name !=null ? userService.getAllByName(name, page) : userService.getAll(page);
		return UserDTO.converter(users);
	}

	@ApiOperation(value = "Listas todos os usuarios do sistema por nivel")
	@GetMapping(path = "/ByNivel")
	public Page<UserDTO> getAllByNivel(@RequestParam RoleUser nivel, @RequestParam int pagina, @RequestParam int qtd, @RequestParam String ordenacao, @RequestParam Boolean ordemAsc) {
		Pageable page = PageRequest.of(pagina, qtd, ordemAsc ? Direction.ASC : Direction.DESC, ordenacao);
		
		Page<UserSistem> users = userService.findByNivel(nivel, page);
		return UserDTO.converter(users);
	}
	
	@ApiOperation(value = "Listas todos os clientes do sistema")
    @GetMapping(path = "/all")
    public List getAll() {
        return userService.getAll();
    }
}
