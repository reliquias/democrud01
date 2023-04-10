package com.example.democrud01.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.democrud01.enums.TipoTransacao;
import com.example.democrud01.model.Caixa;
import com.example.democrud01.model.Transacao;
import com.example.democrud01.repository.CaixaRepository;
import com.example.democrud01.repository.TransacaoRepository;
import com.example.democrud01.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransacaoService {
	
	@Autowired
	private TransacaoRepository transacaoRepository;

	@Autowired
	private CaixaRepository caixaRepository;
	
	public Transacao create(Transacao transacao) {
		Transacao transacaoCad = transacaoRepository.save(transacao);
		return transacaoCad;
	}

	public ResponseEntity<Transacao> update(long id, Transacao transacao) {
		log.info("TransacaoService update ");
		
		Transacao record = transacaoRepository.findById(id).get();
		if(record == null) {
			return ResponseEntity.notFound().build();
		}
		record.setUserSistem(transacao.getUserSistem());
		record.setCliente(transacao.getCliente());
		
		Transacao updated = transacaoRepository.save(record);
		return ResponseEntity.ok().body(updated);
	}
		
	public void deleteById(long id) {
		transacaoRepository.deleteById(id);
	}

	public Optional<Transacao> get(Long id) {
		log.info("Executando TransacaoService metodo getTransacao");
		return transacaoRepository.findById(id);
	}

	public Page<Transacao> getAll(Pageable paginacao) {
		return transacaoRepository.findAll(paginacao);
	}
	
	public List<Transacao> getAll() {
		return transacaoRepository.findAll();
	}
	
	public Page<Transacao> getAlByCaixa(Long idCaixa, Pageable paginacao) {
		Caixa caixa = caixaRepository.findById(idCaixa).get();
		Transacao abertura = new Transacao(new Long("0"), caixa.getUsuarioAbertura(), null, caixa.getValorInicial(), BigDecimal.ZERO, caixa.getValorInicial(), BigDecimal.ZERO, BigDecimal.ZERO, caixa.getDataAbertura(), TipoTransacao.abertura, null, caixa);
		List<Transacao> transacoes = transacaoRepository.findByCaixa(caixa);
		
		transacoes = transacoes
				  .stream()
				  .filter(t -> Utils.substituiNuloPorZero(t.getCartaoCredito()).compareTo(BigDecimal.ZERO) > 0 || Utils.substituiNuloPorZero(t.getCartaoDebito()).compareTo(BigDecimal.ZERO) > 0 || Utils.substituiNuloPorZero(t.getDinheiro()).compareTo(BigDecimal.ZERO) > 0 )
				  .collect(Collectors.toList());
		
		transacoes.add(0, abertura);
		BigDecimal valorMomento = BigDecimal.ZERO;
		List<Transacao> listaFinal = new ArrayList<>();
		
		for (Transacao transacao : transacoes) {
			BigDecimal somaTransacao = Utils.substituiNuloPorZero(transacao.getCartaoCredito()).add(Utils.substituiNuloPorZero(transacao.getCartaoDebito())).add(Utils.substituiNuloPorZero(transacao.getDinheiro()));
			valorMomento = valorMomento.add(somaTransacao);
			transacao.setTotal(valorMomento);
			listaFinal.add(transacao);
		}
		
		List<Transacao> sortedList = listaFinal.stream()
				  .sorted(Comparator.comparing(Transacao::getDataTransacao).reversed())
				  .collect(Collectors.toList());
		
		sortedList.add(0, sumTotal(transacoes));
		Page<Transacao> pages = new PageImpl<>(sortedList, paginacao, sortedList.size());
		return pages;
	}
	
	private Transacao sumTotal(List<Transacao> transacoes) {
		BiConsumer<Transacao, Transacao> reducer = (o1, o2) -> {
		    o1.setDinheiro(Utils.substituiNuloPorZero(o1.getDinheiro()).add(Utils.substituiNuloPorZero(o2.getDinheiro())));
		    o1.setCartaoCredito(Utils.substituiNuloPorZero(o1.getCartaoCredito()).add(Utils.substituiNuloPorZero(o2.getCartaoCredito())));
		    o1.setCartaoDebito(Utils.substituiNuloPorZero(o1.getCartaoDebito()).add(Utils.substituiNuloPorZero(o2.getCartaoDebito())));
		};
		
		Transacao transacao = transacoes.stream()
		        .collect(() -> new Transacao(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO), reducer, reducer);
		
		BigDecimal somaTransacao = Utils.substituiNuloPorZero(transacao.getCartaoCredito()).add(Utils.substituiNuloPorZero(transacao.getCartaoDebito())).add(Utils.substituiNuloPorZero(transacao.getDinheiro()));
		
		transacao.setTipo(TipoTransacao.total);
		transacao.setTotal(somaTransacao);
		transacao.setId(new Long(0));
		return transacao;
	}

	public Page<Transacao> getAllVendasByCaixa(Long idCaixa, Pageable paginacao) {
		Caixa caixa = caixaRepository.getById(idCaixa);
		return transacaoRepository.findAllByTipoAndCaixa(TipoTransacao.venda, caixa, paginacao);
	}
}