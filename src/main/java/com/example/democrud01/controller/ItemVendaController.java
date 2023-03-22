package com.example.democrud01.controller;

import java.net.URI;
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

import com.example.democrud01.dto.ItemVendaDTO;
import com.example.democrud01.dto.ItemVendaForm;
import com.example.democrud01.model.ItemVenda;
import com.example.democrud01.model.Venda;
import com.example.democrud01.service.ItemVendaService;
import com.example.democrud01.service.ProdutoService;
import com.example.democrud01.service.VendaService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({"/api/itemVenda"})
@CrossOrigin(origins = {"http://localhost:4200/", "http://www.505crm.com.br/"}, maxAge = 3600, allowCredentials = "true", allowedHeaders = "*")
public class ItemVendaController {
	
	@Autowired
    private ItemVendaService itemVendaService;
	
	@Autowired
    private ProdutoService produtoService;
	
	@Autowired
    private VendaService vendaService;
	
	@ApiOperation(value = "Cadastrar um novo ItemVenda do sistema")
	@PostMapping
    public ResponseEntity<ItemVendaDTO> create(@RequestBody ItemVendaForm form, UriComponentsBuilder uriBuilder) {
        ItemVenda itemVenda = form.converter(produtoService, vendaService);
        itemVendaService.create(itemVenda);
		URI uri = uriBuilder.path("/api/itemVenda/{id}").buildAndExpand(itemVenda.getId()).toUri();
        return ResponseEntity.created(uri).body(new ItemVendaDTO(itemVenda));
    }

	@ApiOperation(value = "Alterar ItemVenda do sistema")
	@PutMapping(value="/{id}")
	public ResponseEntity<ItemVendaDTO> update(@PathVariable("id") long id, @RequestBody ItemVendaForm form, UriComponentsBuilder uriBuilder) {
		ItemVenda itemVenda = form.converter(produtoService, vendaService);
		itemVendaService.update(id, itemVenda);
		URI uri = uriBuilder.path("/api/itemVenda/{id}").buildAndExpand(itemVenda.getId()).toUri();
        return ResponseEntity.created(uri).body(new ItemVendaDTO(itemVenda));
	}

	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity <?> delete(@PathVariable long id) {
	   return itemVendaService.get(id)
	           .map(record -> {
	        	   itemVendaService.deleteById(id);
	               return ResponseEntity.ok().build();
	           }).orElse(ResponseEntity.notFound().build());
	}

	@ApiOperation(value = "Procura um ItemVenda pelo id")
    @GetMapping(path = "/{id}")
    public ResponseEntity<ItemVendaDTO> get(@PathVariable final Long id) {
		ItemVenda itemVenda = itemVendaService.get(id).get();
    	return ResponseEntity.ok().body(new ItemVendaDTO(itemVenda));
    }

	@ApiOperation(value = "Listas todos os itemVendas do sistema")
	@GetMapping
	public Page<ItemVendaDTO> getAll(@RequestParam int pagina, @RequestParam int qtd, @RequestParam String ordenacao, @RequestParam Boolean ordemAsc) {
		Pageable page = PageRequest.of(pagina, qtd, ordemAsc ? Direction.ASC : Direction.DESC, ordenacao);
		
		Page<ItemVenda> itemVendas = itemVendaService.getAll(page);
		return ItemVendaDTO.converter(itemVendas);
	}

	@ApiOperation(value = "Listas todos os ItemVendas do sistema")
    @GetMapping(path = "/all")
    public List getAll() {
        return itemVendaService.getAll();
    }
	
	@ApiOperation(value = "Listas todos os itens da venda")
	@GetMapping(path = "/ByVenda")
	public Page<ItemVendaDTO> getAllByVenda(@RequestParam Long idVenda, @RequestParam int pagina, @RequestParam int qtd, @RequestParam String ordenacao, @RequestParam Boolean ordemAsc) {
		Optional<Venda> venda = vendaService.get(idVenda);
		Pageable page = PageRequest.of(pagina, qtd, ordemAsc ? Direction.ASC : Direction.DESC, ordenacao);
		
		Page<ItemVenda> itens = itemVendaService.findByVenda(venda.get(), page);
		return ItemVendaDTO.converter(itens);
	}
}
