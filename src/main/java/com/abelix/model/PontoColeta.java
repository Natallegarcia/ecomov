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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pontos_coleta")
public class PontoColeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bairro_id", nullable = false)
    private Bairro bairro;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String responsavel;

    @Column(name = "telefone_responsavel")
    private String telefoneResponsavel;

    @Column(name = "email_responsavel")
    private String emailResponsavel;

    @Column(nullable = false)
    private String endereco;

    @Column(name = "horario_funcionamento")
    private String horarioFuncionamento;

    @ElementCollection
    @CollectionTable(
        name = "tipos_residuo_ponto",
        joinColumns = @JoinColumn(name = "ponto_id")
    )
    @Column(name = "tipo_residuo", nullable = false)
    private List<String> tiposResiduoAceitos;

    public PontoColeta() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getTelefoneResponsavel() {
        return telefoneResponsavel;
    }

    public void setTelefoneResponsavel(String telefoneResponsavel) {
        this.telefoneResponsavel = telefoneResponsavel;
    }

    public String getEmailResponsavel() {
        return emailResponsavel;
    }

    public void setEmailResponsavel(String emailResponsavel) {
        this.emailResponsavel = emailResponsavel;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getHorarioFuncionamento() {
        return horarioFuncionamento;
    }

    public void setHorarioFuncionamento(String horarioFuncionamento) {
        this.horarioFuncionamento = horarioFuncionamento;
    }

    public List<String> getTiposResiduoAceitos() {
        return tiposResiduoAceitos;
    }

    public void setTiposResiduoAceitos(List<String> tiposResiduoAceitos) {
        this.tiposResiduoAceitos = tiposResiduoAceitos;
    }
}
