package com.example.democrud01.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

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

import com.example.democrud01.dto.CaixaDTO;
import com.example.democrud01.dto.CaixaForm;
import com.example.democrud01.model.Caixa;
import com.example.democrud01.model.UserSistem;
import com.example.democrud01.model.Venda;
import com.example.democrud01.service.CaixaService;
import com.example.democrud01.service.UserService;
import com.example.democrud01.service.VendaService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({"/api/caixa"})
@CrossOrigin(origins = {"http://localhost:4200/", "http://www.505crm.com.br/"}, maxAge = 3600, allowCredentials = "true", allowedHeaders = "*")
public class CaixaController {
	
	@Autowired
    private CaixaService caixaService;
	
	@Autowired
    private UserService userService;

	@Autowired
	private VendaService vendaService;
	
	@ApiOperation(value = "Cadastrar um novo Caixa do sistema")
	@PostMapping
    public ResponseEntity<CaixaDTO> create(@RequestBody CaixaForm form, UriComponentsBuilder uriBuilder) {
        Caixa caixa = form.converter(userService);
        caixaService.create(caixa);
        URI uri = uriBuilder.path("/api/caixa/{id}").buildAndExpand(caixa.getId()).toUri();
        return ResponseEntity.created(uri).body(new CaixaDTO(caixa));
    }
	
	@ApiOperation(value = "Abrir Caixa")
	@PostMapping(path = "/open")
    public ResponseEntity<CaixaDTO> aberturaCaixa(@RequestParam Long idUsuarioAbertura, @RequestParam BigDecimal valorInicial, UriComponentsBuilder uriBuilder) {
        Caixa caixa = new Caixa();
        caixa.setDataAbertura(Calendar.getInstance());
        caixa.setValorInicial(valorInicial);
        UserSistem usuarioAbertura = userService.get(idUsuarioAbertura).get();
        caixa.setUsuarioAbertura(usuarioAbertura);
        caixaService.create(caixa);
        URI uri = uriBuilder.path("/api/caixa/{id}").buildAndExpand(caixa.getId()).toUri();
        return ResponseEntity.created(uri).body(new CaixaDTO(caixa));
    }
	
	@ApiOperation(value = "Fechar o Caixa")
	@PutMapping(value="/close/{id}")
	public ResponseEntity<CaixaDTO> fechamentoCaixa(@PathVariable("id") long id, @RequestParam Long idUsuarioFechamento, @RequestParam BigDecimal valorTotalReal, UriComponentsBuilder uriBuilder) {
		Caixa caixa = new Caixa();
		
		caixa.setDataFechamento(Calendar.getInstance());
        UserSistem usuarioFechamento = userService.get(idUsuarioFechamento).get();
        caixa.setUsuarioFechamento(usuarioFechamento);
        caixa.setValorTotalReal(valorTotalReal);
		
		caixa = caixaService.fechamentoCaixa(id, caixa).getBody();
		URI uri = uriBuilder.path("/api/caixa/{id}").buildAndExpand(caixa.getId()).toUri();
        return ResponseEntity.created(uri).body(new CaixaDTO(caixa));
	}
	
	@ApiOperation(value = "Alterar Caixa do sistema")
	@PutMapping(value="/{id}")
	public ResponseEntity<CaixaDTO> update(@PathVariable("id") long id, @RequestBody CaixaForm form, UriComponentsBuilder uriBuilder) {
		Caixa caixa = form.converter(userService);
		caixaService.update(id, caixa);
		URI uri = uriBuilder.path("/api/caixa/{id}").buildAndExpand(caixa.getId()).toUri();
        return ResponseEntity.created(uri).body(new CaixaDTO(caixa));
	}

	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity <?> delete(@PathVariable long id) {
	   return caixaService.get(id)
	           .map(record -> {
	        	   caixaService.deleteById(id);
	               return ResponseEntity.ok().build();
	           }).orElse(ResponseEntity.notFound().build());
	}

	@ApiOperation(value = "Procura um Caixa pelo id")
    @GetMapping(path = "/{id}")
    public ResponseEntity<CaixaDTO> get(@PathVariable final Long id) {
		Caixa caixa = caixaService.get(id).get();
    	return ResponseEntity.ok().body(new CaixaDTO(caixa));
    }
	
	@ApiOperation(value = "Procura um Caixa aberto")
    @GetMapping(path = "/opened-box")
    public ResponseEntity<CaixaDTO> getOpenedBox() {
		Caixa caixa = caixaService.getOpened().get();
    	return ResponseEntity.ok().body(new CaixaDTO(caixa));
    }

	@ApiOperation(value = "Procura um Caixa aberto")
	@GetMapping(path = "/opened-by-user")
	public ResponseEntity<CaixaDTO> getOpenedByUser(@RequestParam Long idUsuarioAbertura) {
		Optional<Caixa> optCaixa = caixaService.getOpenedByUser(idUsuarioAbertura);
		if (optCaixa.isPresent()) {
			Caixa caixa = optCaixa.get();
			return ResponseEntity.ok().body(new CaixaDTO(caixa));
		} else {
			return ResponseEntity.ok().body(new CaixaDTO());
		}
	}

	@ApiOperation(value = "Listas todos os caixas do sistema")
	@GetMapping
	public Page<CaixaDTO> getAll(@RequestParam int pagina, @RequestParam int qtd, @RequestParam String ordenacao, @RequestParam Boolean ordemAsc) {
		Pageable page = PageRequest.of(pagina, qtd, ordemAsc ? Direction.ASC : Direction.DESC, ordenacao);
		
		Page<Caixa> caixas = caixaService.getAll(page);
		return CaixaDTO.converter(caixas);
	}

	@ApiOperation(value = "Listas todos os Caixas do sistema")
    @GetMapping(path = "/all")
    public List getAll() {
        return caixaService.getAll();
    }
}
