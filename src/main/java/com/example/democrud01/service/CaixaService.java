package com.example.democrud01.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.democrud01.model.Caixa;
import com.example.democrud01.model.UserSistem;
import com.example.democrud01.model.Venda;
import com.example.democrud01.repository.CaixaRepository;
import com.example.democrud01.repository.UserRepository;
import com.example.democrud01.repository.VendaRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CaixaService {
	
	@Autowired
	private CaixaRepository caixaRepository;

	@Autowired
	private VendaRepository vendaRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Caixa create(Caixa caixa) {
		Caixa caixaCad = caixaRepository.save(caixa);
		return caixaCad;
	}

	public ResponseEntity<Caixa> update(long id, Caixa caixa) {
		log.info("CaixaService update ");
		
		Caixa record = caixaRepository.findById(id).get();
		if(record == null) {
			return ResponseEntity.notFound().build();
		}
		record.setDataFechamento(caixa.getDataFechamento());
		record.setUsuarioFechamento(caixa.getUsuarioFechamento());
		
		Caixa updated = caixaRepository.save(record);
		return ResponseEntity.ok().body(updated);
	}

	public ResponseEntity<Caixa> fechamentoCaixa(long id, Caixa caixa) {
		log.info("CaixaService fechamento ");
		
		Caixa record = caixaRepository.findById(id).get();
		if(record == null) {
			return ResponseEntity.notFound().build();
		}
		record.setDataFechamento(caixa.getDataFechamento());
		record.setUsuarioFechamento(caixa.getUsuarioFechamento());
		record.setValorTotalReal(caixa.getValorTotalReal());
		
		List<Venda> vendas = vendaRepository.findAllByCaixa(record);
		BigDecimal sum = vendas.stream().map(x -> x.getTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
		record.setValorTotal(sum.add(record.getValorInicial()));
		Caixa updated = caixaRepository.save(record);
		return ResponseEntity.ok().body(updated);
	}
		
	public void deleteById(long id) {
		caixaRepository.deleteById(id);
	}

	public Optional<Caixa> get(Long id) {
		log.info("Executando CaixaService metodo getCaixa");
		return caixaRepository.findById(id);
	}
	
	public Optional<Caixa> getOpened() {
		log.info("Executando CaixaService metodo getOpened");
		Caixa record = caixaRepository.findByDataFechamentoIsNull().get();
		List<Venda> vendas = vendaRepository.findAllByCaixa(record);
		BigDecimal sum = vendas.stream().map(x -> x.getTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
		record.setValorTotal(sum.add(record.getValorInicial()));
		return Optional.of(record);
	}

	public Optional<Caixa> getOpenedByUser(Long idUsuarioAbertura) {
		log.info("Executando CaixaService metodo getOpenedByUser");

		UserSistem usuario = userRepository.getById(idUsuarioAbertura);
		Optional<Caixa> optCaixa = caixaRepository.findByUsuarioAberturaAndDataFechamentoIsNull(usuario);
		if (optCaixa.isPresent()) {
			Caixa record = caixaRepository.findByUsuarioAberturaAndDataFechamentoIsNull(usuario).get();
			List<Venda> vendas = vendaRepository.findAllByCaixa(record);
			BigDecimal sum = vendas.stream().map(x -> x.getTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
			record.setValorTotal(sum.add(record.getValorInicial()));
			return Optional.of(record);
		} else {
			return optCaixa;
		}
	}

	public Page<Caixa> getAll(Pageable paginacao) {
		return caixaRepository.findAll(paginacao);
	}
	
	public List<Caixa> getAll() {
		return caixaRepository.findAll();
	}
}