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

import com.example.democrud01.dto.SelectUsuarioFiltroDTO;
import com.example.democrud01.dto.SelectUsuarioFiltroForm;
import com.example.democrud01.model.SelectUsuarioFiltro;
import com.example.democrud01.service.SelectUsuarioFiltroService;
import com.example.democrud01.service.SelectUsuarioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({"/api/selectUsuarioFiltro"})
@Api(value = "SelectUsuarioFiltro")
@CrossOrigin(origins = {"http://localhost:4200/", "http://www.505crm.com.br/"}, maxAge = 3600, allowCredentials = "true", allowedHeaders = "*")
public class SelectUsuarioFiltroController {

	@Autowired
	private SelectUsuarioFiltroService selectUsuarioFiltroService;
	
	@Autowired
	private SelectUsuarioService selectUsuarioService;

	@ApiOperation(value = "Cadastrar um novo selectUsuarioFiltro do sistema")
    @PostMapping
    public ResponseEntity<SelectUsuarioFiltroDTO> create(@RequestBody SelectUsuarioFiltroForm form, UriComponentsBuilder uriBuilder) {
        SelectUsuarioFiltro selectUsuarioFiltro = form.converter(selectUsuarioService);
        selectUsuarioFiltroService.create(selectUsuarioFiltro);
		URI uri = uriBuilder.path("/api/selectUsuarioFiltro/{id}").buildAndExpand(selectUsuarioFiltro.getId()).toUri();
        return ResponseEntity.created(uri).body(new SelectUsuarioFiltroDTO(selectUsuarioFiltro));
    }

	@ApiOperation(value = "Alterar Pedido de Venda do sistema")
	@PutMapping(value="/{id}")
	public ResponseEntity<SelectUsuarioFiltroDTO> update(@PathVariable("id") long id, @RequestBody SelectUsuarioFiltroForm form, UriComponentsBuilder uriBuilder) {
		SelectUsuarioFiltro selectUsuarioFiltro = form.converter(selectUsuarioService);
		selectUsuarioFiltroService.update(id, selectUsuarioFiltro);
		URI uri = uriBuilder.path("/api/selectUsuarioFiltro/{id}").buildAndExpand(selectUsuarioFiltro.getId()).toUri();
        return ResponseEntity.created(uri).body(new SelectUsuarioFiltroDTO(selectUsuarioFiltro));
	}

	@ApiOperation(value = "Procura um selectUsuarioFiltro pelo id")
    @GetMapping(path = "/{id}")
    public ResponseEntity get(@PathVariable final Long id) {
    	return selectUsuarioFiltroService.get(id)
 	           .map(record -> ResponseEntity.ok().body(record))
 	           .orElse(ResponseEntity.notFound().build());
    }
	
	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity <?> delete(@PathVariable long id) {
	   return selectUsuarioFiltroService.get(id)
	           .map(record -> {
	        	   selectUsuarioFiltroService.deleteById(id);
	               return ResponseEntity.ok().build();
	           }).orElse(ResponseEntity.notFound().build());
	}
    
	@ApiOperation(value = "Listas todos os selectUsuarioFiltros do sistema")
    @GetMapping
    public Page<SelectUsuarioFiltroDTO> getAll(@RequestParam int pagina, @RequestParam int qtd, @RequestParam String ordenacao, @RequestParam Boolean ordemAsc ) {
		Pageable page = PageRequest.of(pagina, qtd, ordemAsc ? Direction.ASC : Direction.DESC, ordenacao);
		
		Page<SelectUsuarioFiltro> selectUsuarioFiltros = selectUsuarioFiltroService.getAll(page);
        return SelectUsuarioFiltroDTO.converter(selectUsuarioFiltros);
    }
	
	@ApiOperation(value = "Listas todos os selectUsuarioFiltros do Relatorio")
	@GetMapping(path = "/bySelect/{idRelatorio}")
	public Page<SelectUsuarioFiltroDTO> findBySelectUsuario(@PathVariable long idRelatorio, @RequestParam int pagina, @RequestParam int qtd, @RequestParam String ordenacao, @RequestParam Boolean ordemAsc ) {
		Pageable page = PageRequest.of(pagina, qtd, ordemAsc ? Direction.ASC : Direction.DESC, ordenacao);
		
		Page<SelectUsuarioFiltro> pedidoVendas = selectUsuarioFiltroService.findBySelectUsuario(idRelatorio, page);
		return SelectUsuarioFiltroDTO.converter(pedidoVendas);
	}
}
