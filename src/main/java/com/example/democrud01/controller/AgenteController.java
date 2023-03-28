package com.example.democrud01.controller;

import java.math.BigDecimal;
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

import com.example.democrud01.dto.AgenteDTO;
import com.example.democrud01.dto.AgenteForm;
import com.example.democrud01.enums.TipoAgente;
import com.example.democrud01.model.Agente;
import com.example.democrud01.service.AgenteService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({"/api/agente"})
@CrossOrigin(origins = {"http://localhost:4200/", "http://www.505crm.com.br/"}, maxAge = 3600, allowCredentials = "true", allowedHeaders = "*")
public class AgenteController {
	
	@Autowired
    private AgenteService agenteService;
	
	@ApiOperation(value = "Cadastrar um novo Agente do sistema")
	@PostMapping
    public ResponseEntity<AgenteDTO> create(@RequestBody AgenteForm form, UriComponentsBuilder uriBuilder) {
        Agente agente = form.converter();
        agenteService.create(agente);
		URI uri = uriBuilder.path("/api/agente/{id}").buildAndExpand(agente.getId()).toUri();
        return ResponseEntity.created(uri).body(new AgenteDTO(agente));
    }

	@ApiOperation(value = "Alterar Agente do sistema")
	@PutMapping(value="/{id}")
	public ResponseEntity<AgenteDTO> update(@PathVariable("id") long id, @RequestBody AgenteForm form, UriComponentsBuilder uriBuilder) {
		Agente agente = form.converter();
		agenteService.update(id, agente);
		URI uri = uriBuilder.path("/api/agente/{id}").buildAndExpand(agente.getId()).toUri();
        return ResponseEntity.created(uri).body(new AgenteDTO(agente));
	}
	
	@ApiOperation(value = "Alterar Agente do sistema")
	@PutMapping(value="/updateCredito")
	public ResponseEntity<AgenteDTO> updateCredito(@RequestParam("id") long id, @RequestParam BigDecimal valor, UriComponentsBuilder uriBuilder) {
		Agente agente = agenteService.updateCredito(id, valor);
		URI uri = uriBuilder.path("/api/agente/updateCredito").buildAndExpand(agente.getId()).toUri();
        return ResponseEntity.created(uri).body(new AgenteDTO(agente));
	}

	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity <?> delete(@PathVariable long id) {
	   return agenteService.get(id)
	           .map(record -> {
	        	   agenteService.deleteById(id);
	               return ResponseEntity.ok().build();
	           }).orElse(ResponseEntity.notFound().build());
	}

	@ApiOperation(value = "Procura um Agente pelo id")
    @GetMapping(path = "/{id}")
    public ResponseEntity<AgenteDTO> get(@PathVariable final Long id) {
		Agente agente = agenteService.get(id).get();
    	return ResponseEntity.ok().body(new AgenteDTO(agente));
    }

	@ApiOperation(value = "Listas todos os agentes do sistema")
	@GetMapping
	public Page<AgenteDTO> getAll(@RequestParam int pagina, @RequestParam int qtd, @RequestParam String ordenacao, @RequestParam Boolean ordemAsc,@RequestParam(required=false) String name) {
		Pageable page = PageRequest.of(pagina, qtd, ordemAsc ? Direction.ASC : Direction.DESC, ordenacao);
		
		Page<Agente> agentes = name !=null ? agenteService.getAllByName(name, page) : agenteService.getAll(page);
		return AgenteDTO.converter(agentes);
	}

	@ApiOperation(value = "Listas todos os agentes do sistema por tipo")
	@GetMapping(path = "/ByTipo")
	public Page<AgenteDTO> getAllByTipo(@RequestParam TipoAgente tipo, @RequestParam int pagina, @RequestParam int qtd, @RequestParam String ordenacao, @RequestParam Boolean ordemAsc) {
		Pageable page = PageRequest.of(pagina, qtd, ordemAsc ? Direction.ASC : Direction.DESC, ordenacao);
		
		Page<Agente> agentes = agenteService.findByTipo(tipo, page);
		return AgenteDTO.converter(agentes);
	}
	
	@ApiOperation(value = "Listas todos os Agentes do sistema")
    @GetMapping(path = "/all")
    public List getAll() {
        return agenteService.getAll();
    }
}
