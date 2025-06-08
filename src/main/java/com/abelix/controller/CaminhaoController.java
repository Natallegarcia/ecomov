package com.abelix.controller;



import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abelix.model.Caminhao;
import com.abelix.repository.CaminhaoRepository;


@RestController
@RequestMapping("/caminhoes")

public class CaminhaoController {
	private final CaminhaoRepository repository;

    public CaminhaoController(CaminhaoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Caminhao> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Caminhao salvar(@RequestBody Caminhao caminhao) {
        return repository.save(caminhao);
    }

}
