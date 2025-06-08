package com.abelix.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abelix.model.Conexao;
import com.abelix.repository.ConexaoRepository;

@RestController
@RequestMapping("/conexoes")
public class ConexaoController {

	@Autowired
    private ConexaoRepository conexaoRepository;

    @GetMapping
    public List<Conexao> listarTodas() {
        return conexaoRepository.findAll();
    }

    @PostMapping
    public Conexao salvar(@RequestBody Conexao conexao) {
        return conexaoRepository.save(conexao);
    }


}
