package com.abelix.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "itinerarios")

public class Itinerario {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_coleta", nullable = false)
    private LocalDate dataColeta;

    @Column(name = "caminhao_id", nullable = false)
    private Long caminhaoId;

    @Column(name = "rota_id", nullable = false)
    private Long rotaId;

    public Itinerario() {}

    public Itinerario(LocalDate dataColeta, Long caminhaoId, Long rotaId) {
        this.dataColeta = dataColeta;
        this.caminhaoId = caminhaoId;
        this.rotaId = rotaId;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDataColeta() {
		return dataColeta;
	}

	public void setDataColeta(LocalDate dataColeta) {
		this.dataColeta = dataColeta;
	}

	public Long getCaminhaoId() {
		return caminhaoId;
	}

	public void setCaminhaoId(Long caminhaoId) {
		this.caminhaoId = caminhaoId;
	}

	public Long getRotaId() {
		return rotaId;
	}

	public void setRotaId(Long rotaId) {
		this.rotaId = rotaId;
	}
    
    

}
