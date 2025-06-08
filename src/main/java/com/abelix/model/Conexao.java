package com.abelix.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "conexoes")
public class Conexao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bairro_origem_id", nullable = false)
    private Bairro origem;

    @ManyToOne
    @JoinColumn(name = "bairro_destino_id", nullable = false)
    private Bairro destino;

    @Column(nullable = false)
    private Double distanciaKm;

    public Conexao() {
    }

    public Conexao(Bairro origem, Bairro destino, Double distanciaKm) {
        this.origem = origem;
        this.destino = destino;
        this.distanciaKm = distanciaKm;
    }

    public Long getId() {
        return id;
    }

    public Bairro getOrigem() {
        return origem;
    }

    public Bairro getDestino() {
        return destino;
    }

    public Double getDistanciaKm() {
        return distanciaKm;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrigem(Bairro origem) {
        this.origem = origem;
    }

    public void setDestino(Bairro destino) {
        this.destino = destino;
    }

    public void setDistanciaKm(Double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }
}
