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

import com.example.democrud01.dto.ItemTransacaoDTO;
import com.example.democrud01.dto.ItemTransacaoForm;
import com.example.democrud01.model.ItemTransacao;
import com.example.democrud01.model.Transacao;
import com.example.democrud01.service.ItemTransacaoService;
import com.example.democrud01.service.ProdutoService;
import com.example.democrud01.service.TransacaoService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({"/api/itemTransacao"})
@CrossOrigin(origins = {"http://localhost:4200/", "http://www.505crm.com.br/"}, maxAge = 3600, allowCredentials = "true", allowedHeaders = "*")
public class ItemTransacaoController {
	
	@Autowired
    private ItemTransacaoService itemTransacaoService;
	
	@Autowired
    private ProdutoService produtoService;
	
	@Autowired
    private TransacaoService transacaoService;
	
	@ApiOperation(value = "Cadastrar um novo ItemTransacao do sistema")
	@PostMapping
    public ResponseEntity<ItemTransacaoDTO> create(@RequestBody ItemTransacaoForm form, UriComponentsBuilder uriBuilder) {
        ItemTransacao itemTransacao = form.converter(produtoService, transacaoService);
        itemTransacaoService.create(itemTransacao);
		URI uri = uriBuilder.path("/api/itemTransacao/{id}").buildAndExpand(itemTransacao.getId()).toUri();
        return ResponseEntity.created(uri).body(new ItemTransacaoDTO(itemTransacao));
    }

	@ApiOperation(value = "Alterar ItemTransacao do sistema")
	@PutMapping(value="/{id}")
	public ResponseEntity<ItemTransacaoDTO> update(@PathVariable("id") long id, @RequestBody ItemTransacaoForm form, UriComponentsBuilder uriBuilder) {
		ItemTransacao itemTransacao = form.converter(produtoService, transacaoService);
		itemTransacaoService.update(id, itemTransacao);
		URI uri = uriBuilder.path("/api/itemTransacao/{id}").buildAndExpand(itemTransacao.getId()).toUri();
        return ResponseEntity.created(uri).body(new ItemTransacaoDTO(itemTransacao));
	}

	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity <?> delete(@PathVariable long id) {
	   return itemTransacaoService.get(id)
	           .map(record -> {
	        	   itemTransacaoService.deleteById(id);
	               return ResponseEntity.ok().build();
	           }).orElse(ResponseEntity.notFound().build());
	}

	@ApiOperation(value = "Procura um ItemTransacao pelo id")
    @GetMapping(path = "/{id}")
    public ResponseEntity<ItemTransacaoDTO> get(@PathVariable final Long id) {
		ItemTransacao itemTransacao = itemTransacaoService.get(id).get();
    	return ResponseEntity.ok().body(new ItemTransacaoDTO(itemTransacao));
    }

	@ApiOperation(value = "Listas todos os itemTransacaos do sistema")
	@GetMapping
	public Page<ItemTransacaoDTO> getAll(@RequestParam int pagina, @RequestParam int qtd, @RequestParam String ordenacao, @RequestParam Boolean ordemAsc) {
		Pageable page = PageRequest.of(pagina, qtd, ordemAsc ? Direction.ASC : Direction.DESC, ordenacao);
		
		Page<ItemTransacao> itemTransacaos = itemTransacaoService.getAll(page);
		return ItemTransacaoDTO.converter(itemTransacaos);
	}

	@ApiOperation(value = "Listas todos os ItemTransacaos do sistema")
    @GetMapping(path = "/all")
    public List getAll() {
        return itemTransacaoService.getAll();
    }
	
	@ApiOperation(value = "Listas todos os itens da transacao")
	@GetMapping(path = "/ByTransacao")
	public Page<ItemTransacaoDTO> getAllByTransacao(@RequestParam Long idTransacao, @RequestParam int pagina, @RequestParam int qtd, @RequestParam String ordenacao, @RequestParam Boolean ordemAsc) {
		Optional<Transacao> transacao = transacaoService.get(idTransacao);
		Pageable page = PageRequest.of(pagina, qtd, ordemAsc ? Direction.ASC : Direction.DESC, ordenacao);
		
		Page<ItemTransacao> itens = itemTransacaoService.findByTransacao(transacao.get(), page);
		return ItemTransacaoDTO.converter(itens);
	}
}
