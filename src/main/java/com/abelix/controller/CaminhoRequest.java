package com.abelix.controller;


public class CaminhoRequest {
    private String origem;
    private String destino;

    public CaminhoRequest() {}

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
}

