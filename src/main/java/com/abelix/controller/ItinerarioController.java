package com.abelix.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.abelix.DTO.ItinerarioCalendarioDTO;
import com.abelix.model.Itinerario;
import com.abelix.service.ItinerarioService;

@RestController
@RequestMapping("/itinerarios")
public class ItinerarioController {

    @Autowired
    private ItinerarioService itinerarioService;

    // Criar novo itinerário
    @PostMapping("novoItinerario")
    public ResponseEntity<Itinerario> criar(@RequestBody Itinerario itinerario) {
        try {
            Itinerario novo = itinerarioService.criarItinerario(itinerario);
            return ResponseEntity.ok(novo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Listar todos
    @GetMapping
    public ResponseEntity<List<Itinerario>> listarTodos() {
        return ResponseEntity.ok(itinerarioService.listarTodos());
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Itinerario> buscarPorId(@PathVariable Long id) {
        Optional<Itinerario> itinerario = itinerarioService.buscarPorId(id);
        return itinerario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Excluir
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            itinerarioService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Itinerario> atualizar(@PathVariable Long id, @RequestBody Itinerario itinerario) {
        try {
            itinerario.setId(id);  
            Itinerario atualizado = itinerarioService.atualizar(itinerario);
            return ResponseEntity.ok(atualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    // Buscar por caminhão
    @GetMapping("/caminhao/{id}")
    public ResponseEntity<List<Itinerario>> buscarPorCaminhao(@PathVariable Long id) {
        return ResponseEntity.ok(itinerarioService.buscarPorCaminhao(id));
    }

    // Buscar por data
    @GetMapping("/data/{data}")
    public ResponseEntity<List<Itinerario>> buscarPorData(@PathVariable String data) {
        try {
            LocalDate dataColeta = LocalDate.parse(data);
            return ResponseEntity.ok(itinerarioService.buscarPorData(dataColeta));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Cronograma mensal por caminhão
    @GetMapping("/caminhao/{id}/cronograma-mensal")
    public ResponseEntity<List<ItinerarioCalendarioDTO>> getCronogramaMensal(
            @PathVariable Long id,
            @RequestParam int ano,
            @RequestParam int mes) {
        List<ItinerarioCalendarioDTO> cronograma = itinerarioService.listarItinerariosPorCaminhaoNoMes(id, ano, mes);
        return ResponseEntity.ok(cronograma);
    }
}
