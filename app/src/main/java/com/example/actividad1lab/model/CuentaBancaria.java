package com.example.actividad1lab.model;

public class CuentaBancaria {
    private String cbu;
    private String alias;

    public CuentaBancaria(String cbu, String alias) {
        this.cbu = cbu;
        this.alias = alias;
    }

    public String getCbu() {
        return cbu;
    }

    public String getAlias() {
        return alias;
    }
}
