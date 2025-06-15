package com.abelix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abelix.model.PontoColeta;
import com.abelix.repository.PontoColetaRepository;



@RestController
@RequestMapping("/pontos-coleta")
public class PontoColetaController {

    @Autowired
    private PontoColetaRepository repository;

    @GetMapping
    public List<PontoColeta> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public PontoColeta buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ponto de Coleta não encontrado com id: " + id));
    }

    @PostMapping
    public PontoColeta salvar(@RequestBody PontoColeta ponto) {
        if (repository.existsByNome(ponto.getNome())) {
            throw new RuntimeException("Nome de ponto de coleta já existe");
        }
        return repository.save(ponto);
    }

    @PutMapping("/{id}")
    public PontoColeta atualizar(@PathVariable Long id, @RequestBody PontoColeta pontoAtualizado) {
        PontoColeta pontoExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ponto de Coleta não encontrado com id: " + id));

        // Atualizando os campos 
        pontoExistente.setBairro(pontoAtualizado.getBairro());
        pontoExistente.setNome(pontoAtualizado.getNome());
        pontoExistente.setResponsavel(pontoAtualizado.getResponsavel());
        pontoExistente.setTelefoneResponsavel(pontoAtualizado.getTelefoneResponsavel());
        pontoExistente.setEmailResponsavel(pontoAtualizado.getEmailResponsavel());
        pontoExistente.setEndereco(pontoAtualizado.getEndereco());
        pontoExistente.setHorarioFuncionamento(pontoAtualizado.getHorarioFuncionamento());
        pontoExistente.setTiposResiduoAceitos(pontoAtualizado.getTiposResiduoAceitos());

        return repository.save(pontoExistente);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        PontoColeta ponto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ponto de Coleta não encontrado com id: " + id));

        repository.delete(ponto);
    }
}