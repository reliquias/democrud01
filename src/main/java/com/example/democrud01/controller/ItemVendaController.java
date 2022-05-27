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

import com.example.democrud01.dto.ItemVendaDTO;
import com.example.democrud01.dto.ItemVendaForm;
import com.example.democrud01.model.ItemVenda;
import com.example.democrud01.service.ItemVendaService;
import com.example.democrud01.service.ProdutoService;
import com.example.democrud01.service.VendaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({"/api/itemVenda"})
@Api(value = "ItemVenda")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ItemVendaController {
	
	@Autowired
    private ItemVendaService itemVendaService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private VendaService vendaService;

	@ApiOperation(value = "Cadastrar um novo itemVenda do sistema")
    @PostMapping
    public ResponseEntity<ItemVendaDTO> create(@RequestBody ItemVendaForm form, UriComponentsBuilder uriBuilder) {
		ItemVenda itemVenda = form.converter(vendaService,produtoService);
        itemVendaService.create(itemVenda);
        URI uri = uriBuilder.path("/api/itemVenda/{id}").buildAndExpand(itemVenda.getId()).toUri();
        return ResponseEntity.created(uri).body(new ItemVendaDTO(itemVenda));
    }

	@ApiOperation(value = "Alterar itemVenda do sistema")
	@PutMapping(value="/{id}")
	public ResponseEntity<ItemVendaDTO> update(@PathVariable("id") long id, @RequestBody ItemVendaForm form, UriComponentsBuilder uriBuilder) {
		ItemVenda itemVenda = form.converter(vendaService,produtoService);
		itemVendaService.update(id, itemVenda);
		URI uri = uriBuilder.path("/api/itemVenda/{id}").buildAndExpand(itemVenda.getId()).toUri();
        return ResponseEntity.created(uri).body(new ItemVendaDTO(itemVenda));
	}

	@ApiOperation(value = "Procura um itemVenda pelo id")
    @GetMapping(path = "/{id}")
    public ResponseEntity get(@PathVariable final Long id) {
    	return itemVendaService.get(id)
 	           .map(record -> ResponseEntity.ok().body(record))
 	           .orElse(ResponseEntity.notFound().build());
    }
	
	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity <?> delete(@PathVariable long id) {
	   return itemVendaService.get(id)
	           .map(record -> {
	        	   itemVendaService.deleteById(id);
	               return ResponseEntity.ok().build();
	           }).orElse(ResponseEntity.notFound().build());
	}
    
	@ApiOperation(value = "Listas todos os itemVendas do sistema")
    @GetMapping
    public Page<ItemVendaDTO> getAll(@RequestParam int pagina, @RequestParam int qtd, @RequestParam String ordenacao, @RequestParam Boolean ordemAsc ) {
		Pageable page = PageRequest.of(pagina, qtd, ordemAsc ? Direction.ASC : Direction.DESC, ordenacao);
		
		Page<ItemVenda> itemVendas = itemVendaService.getAll(page);
        return ItemVendaDTO.converter(itemVendas);
    }
}
