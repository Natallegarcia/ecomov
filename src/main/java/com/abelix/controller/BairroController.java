package com.abelix.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<Bairro> buscarPorId(@PathVariable Long id) {
        Optional<Bairro> bairro = bairroRepository.findById(id);
        return bairro.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

  
    @PostMapping
    public Bairro salvar(@RequestBody Bairro bairro) {
        return bairroRepository.save(bairro);
    }

   
    @PutMapping("/{id}")
    public ResponseEntity<Bairro> atualizar(@PathVariable Long id, @RequestBody Bairro bairroAtualizado) {
        Optional<Bairro> bairroExistente = bairroRepository.findById(id);
        if (bairroExistente.isPresent()) {
            Bairro bairro = bairroExistente.get();
            bairro.setNome(bairroAtualizado.getNome());
            bairroRepository.save(bairro);
            return ResponseEntity.ok(bairro);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

   
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (bairroRepository.existsById(id)) {
            bairroRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
