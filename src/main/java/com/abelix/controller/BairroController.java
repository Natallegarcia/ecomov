package com.abelix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abelix.model.Bairro;
import com.abelix.repository.BairroRepository;

@RestController
@RequestMapping("/bairros")
public class BairroController {

	@Autowired
    private BairroRepository bairroRepository;

    @GetMapping
    public List<Bairro> listarTodos() {
        return bairroRepository.findAll();
    }

    @PostMapping
    public Bairro salvar(@RequestBody Bairro bairro) {
        return bairroRepository.save(bairro);
    }


}
