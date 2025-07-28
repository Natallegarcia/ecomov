package com.abelix.controller;



import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.abelix.model.Caminhao;
import com.abelix.model.Rota;
import com.abelix.repository.CaminhaoRepository;

import jakarta.validation.Valid;


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
  
	@GetMapping("/{id}")
    public Caminhao buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
        		.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Caminhão não encontrado"));
    }

    @PostMapping
    public Caminhao salvar(@RequestBody Caminhao caminhao) {
        return repository.save(caminhao);
    }

    @PutMapping
    public Caminhao atualizar(@RequestBody Caminhao caminhao) {
        if (caminhao.getId() == null || !repository.existsById(caminhao.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Caminhão não encontrado");
        }
        return repository.save(caminhao);
    }
    
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Caminhão não encontrado");
        }
        repository.deleteById(id);
    }
    
    

}
