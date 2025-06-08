package com.abelix.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abelix.model.PontoColeta;
import com.abelix.repository.PontoColetaRepository;




@RestController
@RequestMapping("/pontos-coleta")
public class PontoColetaController {

	private final PontoColetaRepository repository;

    public PontoColetaController(PontoColetaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<PontoColeta> listar() {
        return repository.findAll();
    }

    @PostMapping
    public PontoColeta salvar(@RequestBody PontoColeta ponto) {
        if (repository.existsByNome(ponto.getNome())) {
            throw new RuntimeException("Nome de ponto de coleta já existe");
        }
        return repository.save(ponto);
    }


}
