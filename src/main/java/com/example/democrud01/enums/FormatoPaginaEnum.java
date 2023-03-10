package com.example.democrud01.enums;

/**
 *
 * @author mauricio.frison
 */
public enum FormatoPaginaEnum {
	
    RETRATO("enum.formatoPagina.Retrato"),
    PAISAGEM("enum.formatoPagina.Paisagem");
    
    private String key;   
    
	FormatoPaginaEnum(String key) {
        this.key = key;
    }
    
	public String getKey() {
        return key;
    }

}