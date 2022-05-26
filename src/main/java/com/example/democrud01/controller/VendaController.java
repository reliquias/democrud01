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

import com.example.democrud01.dto.VendaDTO;
import com.example.democrud01.dto.VendaForm;
import com.example.democrud01.model.Venda;
import com.example.democrud01.service.ClienteService;
import com.example.democrud01.service.VendaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({"/api/venda"})
@Api(value = "Venda")
@CrossOrigin(origins = "*", maxAge = 3600)
public class VendaController {
	
	@Autowired
    private VendaService vendaService;
	
	@Autowired
	private ClienteService clienteService;

	@ApiOperation(value = "Cadastrar um novo venda do sistema")
    @PostMapping
    public ResponseEntity<VendaDTO> create(@RequestBody VendaForm form, UriComponentsBuilder uriBuilder) {
		Venda venda = form.converter(clienteService);
        vendaService.create(venda);
        URI uri = uriBuilder.path("/api/venda/{id}").buildAndExpand(venda.getId()).toUri();
        return ResponseEntity.created(uri).body(new VendaDTO(venda));
    }

	@ApiOperation(value = "Alterar venda do sistema")
	@PutMapping(value="/{id}")
	public ResponseEntity<VendaDTO> update(@PathVariable("id") long id, @RequestBody VendaForm form, UriComponentsBuilder uriBuilder) {
		Venda venda = form.converter(clienteService);
		vendaService.update(id, venda);
		URI uri = uriBuilder.path("/api/venda/{id}").buildAndExpand(venda.getId()).toUri();
        return ResponseEntity.created(uri).body(new VendaDTO(venda));
	}

	@ApiOperation(value = "Procura um venda pelo id")
    @GetMapping(path = "/{id}")
    public ResponseEntity get(@PathVariable final Long id) {
    	return vendaService.get(id)
 	           .map(record -> ResponseEntity.ok().body(record))
 	           .orElse(ResponseEntity.notFound().build());
    }
	
	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity <?> delete(@PathVariable long id) {
	   return vendaService.get(id)
	           .map(record -> {
	        	   vendaService.deleteById(id);
	               return ResponseEntity.ok().build();
	           }).orElse(ResponseEntity.notFound().build());
	}
    
	@ApiOperation(value = "Listas todos os vendas do sistema")
    @GetMapping
    public Page<VendaDTO> getAll(@RequestParam int pagina, @RequestParam int qtd, @RequestParam String ordenacao, @RequestParam Boolean ordemAsc ) {
		Pageable page = PageRequest.of(pagina, qtd, ordemAsc ? Direction.ASC : Direction.DESC, ordenacao);
		
		Page<Venda> vendas = vendaService.getAll(page);
        return VendaDTO.converter(vendas);
    }
}
