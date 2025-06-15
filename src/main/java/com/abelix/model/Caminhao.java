package com.abelix.model;

import java.util.Set;

import com.abelix.commons.TipoResiduo;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Caminhao {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String placa;

    private String motorista;

    private Double capacidadeCarga;

    @ElementCollection(targetClass = TipoResiduo.class)
    @Enumerated(EnumType.STRING)
    private Set<TipoResiduo> tiposResiduos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getMotorista() {
		return motorista;
	}

	public void setMotorista(String motorista) {
		this.motorista = motorista;
	}

	public Double getCapacidadeCarga() {
		return capacidadeCarga;
	}

	public void setCapacidadeCarga(Double capacidadeCarga) {
		this.capacidadeCarga = capacidadeCarga;
	}

	public Set<TipoResiduo> getTiposResiduos() {
		return tiposResiduos;
	}

	public void setTiposResiduos(Set<TipoResiduo> tiposResiduos) {
		this.tiposResiduos = tiposResiduos;
	}



}
