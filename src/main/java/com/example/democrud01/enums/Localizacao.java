/*
 * Idioma.java
 *
 * Created on 29 de Junho de 2007, 10:37
 *
 */

package com.example.democrud01.enums;

import java.util.Locale;

public enum Localizacao {
	PORTUGUES("pt","BR","dd/MM/yyyy","enum.localizacao.portugues","%d/%m/%Y","dd/MM/yyyy HH:mm:ss"),
	INGLES("en","US","MM-dd-yyyy","enum.localizacao.ingles","%m-%d-%Y","MM-dd-yyyy HH:mm:ss"),
	ESPANHOL("es","ES","dd.MM.yyyy","enum.localizacao.espanhol","%d.%m.%Y","dd.MM.yyyy HH:mm:ss");
	
	private String codigo;
	private String pais;
	private String patternData;
	private String patternDateTime;
	private Locale locale;
	private String key;
	private String jsFormat;

	Localizacao(String codigo, String pais, String patternData, String key, String jsFormat, String patternDateTime){
		this.codigo=codigo;
		this.pais=pais;
		this.patternData=patternData;
		this.patternDateTime=patternDateTime;
		this.locale = new Locale(this.getCodigo(),this.getPais());
		this.key=key;
		this.jsFormat=jsFormat;
	}

	public String getCodigo(){
		return this.codigo;
	}

	public String getPais(){
		return this.pais;
	}

	public String getPatternData() {
		return patternData;
	}

	public String getPatternDateTime() {
		return patternDateTime;
	}

	public Locale getLocale(){
		return this.locale;
	}

	public String getKey(){
		return this.key;
	}

	public static Localizacao getIdioma(Locale idiomaDoLocale){
		if (idiomaDoLocale == null) {
			throw new IllegalArgumentException("locale nulo");
		}
		if (idiomaDoLocale.equals(INGLES.getLocale())){
			return INGLES;
		} else if (idiomaDoLocale.equals(ESPANHOL.getLocale())){
			return ESPANHOL;
		} else{ //assumimos como default o portugues
			return PORTUGUES;
		}
	}

	public String getJsFormat() {
		return jsFormat;
	}

}
