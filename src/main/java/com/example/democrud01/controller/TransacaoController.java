package com.example.democrud01.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
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

import com.example.democrud01.dto.ExtratoDTO;
import com.example.democrud01.dto.ItemTransacaoForm;
import com.example.democrud01.dto.PgtoDebitoForm;
import com.example.democrud01.dto.TransacaoDTO;
import com.example.democrud01.dto.TransacaoForm;
import com.example.democrud01.model.ItemTransacao;
import com.example.democrud01.model.Produto;
import com.example.democrud01.model.Transacao;
import com.example.democrud01.repository.ProdutoRepository;
import com.example.democrud01.service.AgenteService;
import com.example.democrud01.service.CaixaService;
import com.example.democrud01.service.ProdutoService;
import com.example.democrud01.service.TransacaoService;
import com.example.democrud01.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({"/api/transacao"})
@CrossOrigin(origins = {"http://localhost:4200/", "http://www.505crm.com.br/"}, maxAge = 3600, allowCredentials = "true", allowedHeaders = "*")
public class TransacaoController {
	
	@Autowired
    private TransacaoService transacaoService;
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private AgenteService agenteService;
	
	@Autowired
    private CaixaService caixaService;
	
	@Autowired
    private ProdutoService produtoService;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	
	@ApiOperation(value = "Cadastrar um novo Transacao do sistema")
	@PostMapping
    public ResponseEntity<TransacaoDTO> create(@RequestBody TransacaoForm form, UriComponentsBuilder uriBuilder) {
        Transacao transacao = form.converter(userService, agenteService);
        transacaoService.create(transacao);
        URI uri = uriBuilder.path("/api/transacao/{id}").buildAndExpand(transacao.getId()).toUri();
        return ResponseEntity.created(uri).body(new TransacaoDTO(transacao));
    }
	
	@ApiOperation(value = "Cadastrarc um novo Transacao do sistema")
	@PostMapping(path = "registrar-transacao-completa")
    public ResponseEntity<TransacaoDTO> registrarTransacaoCompleta(@RequestBody TransacaoForm form, UriComponentsBuilder uriBuilder) {
        Transacao transacao = form.converterFull(userService, agenteService, caixaService);
        Collection<ItemTransacao> itens = new ArrayList<>();
        
        List<Produto> produtos = new ArrayList<>();
        
        for (ItemTransacaoForm itemForm : form.getItens()) {
            ItemTransacao itemTransacao = itemForm.converterSemTransacao(produtoService);
            itemTransacao.setTransacao(transacao);
            itens.add(itemTransacao);
            Produto produto = itemTransacao.getProduto();
            produto.setEstoqueAtual(produto.getEstoqueAtual().subtract(itemTransacao.getQuantidade()));
            produtos.add(itemTransacao.getProduto());
		}
        transacao.setItensTransacaoCollection(itens);
        transacaoService.create(transacao);
        
        for (Produto produto : produtos) {
        	produtoRepository.save(produto);
		}
        
        if(transacao.getCliente()!=null && transacao.getSaldoDevedor()!=null && transacao.getSaldoDevedor().compareTo(BigDecimal.ZERO) != 0) {
        	agenteService.debitaCredito(transacao.getCliente().getId(), transacao.getSaldoDevedor());
        }
        URI uri = uriBuilder.path("/api/transacao/{id}").buildAndExpand(transacao.getId()).toUri();
        return ResponseEntity.created(uri).body(new TransacaoDTO(transacao));
    }

	@ApiOperation(value = "Alterar Transacao do sistema")
	@PutMapping(value="/{id}")
	public ResponseEntity<TransacaoDTO> update(@PathVariable("id") long id, @RequestBody TransacaoForm form, UriComponentsBuilder uriBuilder) {
		Transacao transacao = form.converter(userService, agenteService);
		transacaoService.update(id, transacao);
		URI uri = uriBuilder.path("/api/transacao/{id}").buildAndExpand(transacao.getId()).toUri();
        return ResponseEntity.created(uri).body(new TransacaoDTO(transacao));
	}

	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity <?> delete(@PathVariable long id) {
	   return transacaoService.get(id)
	           .map(record -> {
	        	   transacaoService.deleteById(id);
	               return ResponseEntity.ok().build();
	           }).orElse(ResponseEntity.notFound().build());
	}

	@ApiOperation(value = "Procura um Transacao pelo id")
    @GetMapping(path = "/{id}")
    public ResponseEntity<TransacaoDTO> get(@PathVariable final Long id) {
		Transacao transacao = transacaoService.get(id).get();
    	return ResponseEntity.ok().body(new TransacaoDTO(transacao));
    }

	@ApiOperation(value = "Listas todos os transacaos do sistema")
	@GetMapping
	public Page<TransacaoDTO> getAll(@RequestParam int pagina, @RequestParam int qtd, @RequestParam String ordenacao, @RequestParam Boolean ordemAsc) {
		Pageable page = PageRequest.of(pagina, qtd, ordemAsc ? Direction.ASC : Direction.DESC, ordenacao);
		
		Page<Transacao> transacaos = transacaoService.getAll(page);
		return TransacaoDTO.converter(transacaos);
	}

	@ApiOperation(value = "Listas todos os transacaos por caixa")
	@GetMapping(path = "/extratoByCaixa")
	public Page<ExtratoDTO> getAllByCaixa(@RequestParam Long idCaixa, @RequestParam int pagina, @RequestParam int qtd, @RequestParam String ordenacao, @RequestParam Boolean ordemAsc) {
		Pageable page = PageRequest.of(pagina, qtd, ordemAsc ? Direction.ASC : Direction.DESC, ordenacao);
		
		Page<Transacao> transacaos = transacaoService.getAlByCaixa(idCaixa, page);
		return ExtratoDTO.converter(transacaos);
	}
	
	@ApiOperation(value = "Listas todos os transacaos por caixa")
	@GetMapping(path = "/allVendasByCaixa")
	public Page<TransacaoDTO> getAllVendasByCaixa(@RequestParam Long idCaixa, @RequestParam int pagina, @RequestParam int qtd, @RequestParam String ordenacao, @RequestParam Boolean ordemAsc) {
		Pageable page = PageRequest.of(pagina, qtd, ordemAsc ? Direction.ASC : Direction.DESC, ordenacao);
		
		Page<Transacao> transacaos = transacaoService.getAllVendasByCaixa(idCaixa, page);
		return TransacaoDTO.converter(transacaos);
	}

	@ApiOperation(value = "Listas todos os Transacaos do sistema")
    @GetMapping(path = "/all")
    public List getAll() {
        return transacaoService.getAll();
    }
	
	
	@ApiOperation(value = "Pagamento de Debito")
	@PostMapping(value="/pgtoDebito")
	public ResponseEntity<TransacaoDTO> pgtoDebito(@RequestBody PgtoDebitoForm form, UriComponentsBuilder uriBuilder) {
		Transacao transacao = form.converter(userService, agenteService, caixaService);
		transacao.setSaldoDevedor(BigDecimal.ZERO);
		transacaoService.create(transacao);
		agenteService.updateCredito(transacao.getCliente().getId(), transacao.getTotal());
		URI uri = uriBuilder.path("/api/transacao/{id}").buildAndExpand(transacao.getId()).toUri();
        return ResponseEntity.created(uri).body(new TransacaoDTO(transacao));
	}
}
