package com.abelix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.abelix.model.Bairro;
import com.abelix.model.Conexao;
import com.abelix.repository.BairroRepository;
import com.abelix.repository.ConexaoRepository;

@RestController
@RequestMapping("/conexoes")
public class ConexaoController {

    @Autowired
    private ConexaoRepository conexaoRepository;

    @Autowired
    private BairroRepository bairroRepository;

    
    @GetMapping
    public List<Conexao> listarTodas() {
        return conexaoRepository.findAll();
    }

   
    @GetMapping("/{id}")
    public Conexao buscarPorId(@PathVariable Long id) {
        return conexaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conexão não encontrada com ID: " + id));
    }

    
    @PostMapping
    public Conexao salvar(@RequestBody Conexao conexao) {
        return conexaoRepository.save(conexao);
    }

    
    @PutMapping("/{id}")
    public Conexao atualizar(@PathVariable Long id, @RequestBody Conexao conexaoAtualizada) {
        Conexao conexaoExistente = conexaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conexão não encontrada com ID: " + id));

        Bairro origem = bairroRepository.findById(conexaoAtualizada.getOrigem().getId())
                .orElseThrow(() -> new RuntimeException("Bairro origem não encontrado"));

        Bairro destino = bairroRepository.findById(conexaoAtualizada.getDestino().getId())
                .orElseThrow(() -> new RuntimeException("Bairro destino não encontrado"));

        conexaoExistente.setOrigem(origem);
        conexaoExistente.setDestino(destino);
        conexaoExistente.setDistanciaKm(conexaoAtualizada.getDistanciaKm());

        return conexaoRepository.save(conexaoExistente);
    }

 
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        Conexao conexao = conexaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conexão não encontrada com ID: " + id));
        conexaoRepository.delete(conexao);
    }
}
