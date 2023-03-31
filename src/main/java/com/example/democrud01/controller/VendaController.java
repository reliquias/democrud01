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

import com.example.democrud01.dto.ItemVendaForm;
import com.example.democrud01.dto.VendaDTO;
import com.example.democrud01.dto.VendaForm;
import com.example.democrud01.enums.RoleUser;
import com.example.democrud01.model.ItemVenda;
import com.example.democrud01.model.Produto;
import com.example.democrud01.model.Venda;
import com.example.democrud01.repository.ProdutoRepository;
import com.example.democrud01.service.AgenteService;
import com.example.democrud01.service.CaixaService;
import com.example.democrud01.service.ProdutoService;
import com.example.democrud01.service.UserService;
import com.example.democrud01.service.VendaService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({"/api/venda"})
@CrossOrigin(origins = {"http://localhost:4200/", "http://www.505crm.com.br/"}, maxAge = 3600, allowCredentials = "true", allowedHeaders = "*")
public class VendaController {
	
	@Autowired
    private VendaService vendaService;
	
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
	
	
	@ApiOperation(value = "Cadastrar um novo Venda do sistema")
	@PostMapping
    public ResponseEntity<VendaDTO> create(@RequestBody VendaForm form, UriComponentsBuilder uriBuilder) {
        Venda venda = form.converter(userService, agenteService);
        vendaService.create(venda);
        URI uri = uriBuilder.path("/api/venda/{id}").buildAndExpand(venda.getId()).toUri();
        return ResponseEntity.created(uri).body(new VendaDTO(venda));
    }
	
	@ApiOperation(value = "Cadastrarc um novo Venda do sistema")
	@PostMapping(path = "registrar-venda-completa")
    public ResponseEntity<VendaDTO> registrarVendaCompleta(@RequestBody VendaForm form, UriComponentsBuilder uriBuilder) {
        Venda venda = form.converterFull(userService, agenteService, caixaService);
        Collection<ItemVenda> itens = new ArrayList<>();
        
        List<Produto> produtos = new ArrayList<>();
        
        for (ItemVendaForm itemForm : form.getItens()) {
            ItemVenda itemVenda = itemForm.converterSemVenda(produtoService);
            itemVenda.setVenda(venda);
            itens.add(itemVenda);
            Produto produto = itemVenda.getProduto();
            produto.setEstoqueAtual(produto.getEstoqueAtual().subtract(itemVenda.getQuantidade()));
            produtos.add(itemVenda.getProduto());
		}
        venda.setItensVendaCollection(itens);
        vendaService.create(venda);
        
        for (Produto produto : produtos) {
        	produtoRepository.save(produto);
		}
        
        if(venda.getCliente()!=null && venda.getSaldoDevedor()!=null && venda.getSaldoDevedor().compareTo(BigDecimal.ZERO) != 0) {
        	agenteService.updateCredito(venda.getCliente().getId(), venda.getSaldoDevedor());
        }
        URI uri = uriBuilder.path("/api/venda/{id}").buildAndExpand(venda.getId()).toUri();
        return ResponseEntity.created(uri).body(new VendaDTO(venda));
    }

	@ApiOperation(value = "Alterar Venda do sistema")
	@PutMapping(value="/{id}")
	public ResponseEntity<VendaDTO> update(@PathVariable("id") long id, @RequestBody VendaForm form, UriComponentsBuilder uriBuilder) {
		Venda venda = form.converter(userService, agenteService);
		vendaService.update(id, venda);
		URI uri = uriBuilder.path("/api/venda/{id}").buildAndExpand(venda.getId()).toUri();
        return ResponseEntity.created(uri).body(new VendaDTO(venda));
	}

	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity <?> delete(@PathVariable long id) {
	   return vendaService.get(id)
	           .map(record -> {
	        	   vendaService.deleteById(id);
	               return ResponseEntity.ok().build();
	           }).orElse(ResponseEntity.notFound().build());
	}

	@ApiOperation(value = "Procura um Venda pelo id")
    @GetMapping(path = "/{id}")
    public ResponseEntity<VendaDTO> get(@PathVariable final Long id) {
		Venda venda = vendaService.get(id).get();
    	return ResponseEntity.ok().body(new VendaDTO(venda));
    }

	@ApiOperation(value = "Listas todos os vendas do sistema")
	@GetMapping
	public Page<VendaDTO> getAll(@RequestParam int pagina, @RequestParam int qtd, @RequestParam String ordenacao, @RequestParam Boolean ordemAsc) {
		Pageable page = PageRequest.of(pagina, qtd, ordemAsc ? Direction.ASC : Direction.DESC, ordenacao);
		
		Page<Venda> vendas = vendaService.getAll(page);
		return VendaDTO.converter(vendas);
	}

	@ApiOperation(value = "Listas todos os vendas por caixa")
	@GetMapping(path = "/allByCaixa")
	public Page<VendaDTO> getAllByCaixa(@RequestParam Long idCaixa, @RequestParam int pagina, @RequestParam int qtd, @RequestParam String ordenacao, @RequestParam Boolean ordemAsc) {
		Pageable page = PageRequest.of(pagina, qtd, ordemAsc ? Direction.ASC : Direction.DESC, ordenacao);
		
		Page<Venda> vendas = vendaService.getAlByCaixa(idCaixa, page);
		return VendaDTO.converter(vendas);
	}

	@ApiOperation(value = "Listas todos os Vendas do sistema")
    @GetMapping(path = "/all")
    public List getAll() {
        return vendaService.getAll();
    }
}
