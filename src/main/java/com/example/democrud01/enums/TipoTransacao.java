package com.example.democrud01.enums;

public enum TipoTransacao {

	venda("venda", "Venda"), 
	devolucao("devolucao", "Devolução do Cliente"),
	suprimento("suprimento", "Suprimento Caixa"), 
	pgto_debito("pgto_debito", "Pagamento Debito"),
	abertura("abertura", "Abertura de Caixa"),
	total("total", "Total"),
	sangria("sangria", "Sangria Caixa");

	private String codigo;
	private String descricao;

	private TipoTransacao(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
}
