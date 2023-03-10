package com.example.democrud01.enums;

public enum TipoDocumento{
    pdf("enum.tipoDocumento.pdf"),
    xls("enum.tipoDocumento.xls"),
    etq("enum.tipoDocumento.etq");

    private String key;

    TipoDocumento(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
