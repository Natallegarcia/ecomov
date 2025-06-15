package com.abelix.model;



import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "rotas")
public class Rota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String caminhaoId;

    @ElementCollection
    @CollectionTable(name = "rota_bairros", joinColumns = @JoinColumn(name = "rota_id"))
    @Column(name = "bairro")
    private List<String> bairrosVisitados;

    @Column(nullable = false)
    private double distanciaTotal;
    
    @Column
    private Double pesoEstimado;

    @ElementCollection
    @CollectionTable(name = "rota_residuos", joinColumns = @JoinColumn(name = "rota_id"))
    @Column(name = "tipo_residuo")
    private List<String> tiposResiduos;

    public Rota() {}

    public Rota(String caminhaoId, List<String> bairrosVisitados, double distanciaTotal, double pesoEstimado, List<String> tiposResiduos) {
        this.caminhaoId = caminhaoId;
        this.bairrosVisitados = bairrosVisitados;
        this.distanciaTotal = distanciaTotal;
        this.tiposResiduos = tiposResiduos;
        this.pesoEstimado = pesoEstimado; 
    }

    public Long getId() { return id; }
    public String getCaminhaoId() { return caminhaoId; }
    public List<String> getBairrosVisitados() { return bairrosVisitados; }
    public double getDistanciaTotal() { return distanciaTotal; }
    public List<String> getTiposResiduos() { return tiposResiduos; }
    
    


    public Double getPesoEstimado() { return pesoEstimado; }
    public void setPesoEstimado(Double pesoEstimado) { this.pesoEstimado = pesoEstimado; }

	public void setId(Long id) { this.id = id; }
    public void setCaminhaoId(String caminhaoId) { this.caminhaoId = caminhaoId; }
    public void setBairrosVisitados(List<String> bairrosVisitados) { this.bairrosVisitados = bairrosVisitados; }
    public void setDistanciaTotal(double distanciaTotal) { this.distanciaTotal = distanciaTotal; }
    public void setTiposResiduos(List<String> tiposResiduos) { this.tiposResiduos = tiposResiduos; }
}
