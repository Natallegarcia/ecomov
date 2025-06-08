package com.abelix.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abelix.model.Bairro;
import com.abelix.model.Rota;
import com.abelix.service.DijkstraService;
import com.abelix.service.RotaService;

@RestController
@RequestMapping("/rotas")
public class RotaController {

    @Autowired
    private RotaService rotaService;

    @Autowired
    private DijkstraService dijkstraService;

    @PostMapping
    public Rota criarRota(@RequestBody Rota rota) {
        return rotaService.salvarRota(rota);
    }

    @GetMapping
    public List<Rota> listarTodas() {
        return rotaService.listarTodas();
    }

    @GetMapping("/{id}")
    public Rota buscarPorId(@PathVariable Long id) {
        return rotaService.buscarPorId(id);
    }

    @GetMapping("/")
    public String home() {
        return "API de Rotas de Coleta Seletiva - Ecoville está no ar!";
    }
    
    // ✅ 1. Caminho mínimo: apenas os nomes dos bairro
    @GetMapping("/caminho")
    public ResponseEntity<List<String>> calcularCaminhoMaisCurto(
            @RequestParam String origem,
            @RequestParam String destino) {
    		
        System.out.println("Acessando /rotas/caminho");
    	    System.out.println("Origem: " + origem);
    	    System.out.println("Destino: " + destino);

        List<Bairro> caminho = dijkstraService.calcularCaminho(origem, destino);

        if (caminho == null || caminho.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<String> nomes = caminho.stream()
                .map(Bairro::getNome)
                .toList();

        return ResponseEntity.ok(nomes);
    }

    @GetMapping("/caminho-completo")
    public ResponseEntity<Map<String, Object>> calcularCaminhoComDistancia(
            @RequestParam String origem,
            @RequestParam String destino) {

        try {
            List<Bairro> caminho = dijkstraService.calcularCaminho(origem, destino);
            
            if (caminho == null || caminho.isEmpty()) {
                return ResponseEntity.status(404)
                        .body(Map.of("erro", "Caminho não encontrado entre os bairros informados."));
            }

            double distancia = dijkstraService.calcularDistanciaTotal(origem, destino);

            List<String> nomes = caminho.stream()
                    .map(Bairro::getNome)
                    .toList();

            Map<String, Object> resposta = new LinkedHashMap<>();
            resposta.put("rota", nomes);
            resposta.put("distanciaKm", distancia);

            return ResponseEntity.ok(resposta);

        } catch (Exception e) {
            // Retorna erro genérico com mensagem útil
            Map<String, Object> erro = new LinkedHashMap<>();
            erro.put("mensagem", "Erro ao calcular caminho completo.");
            erro.put("detalhes", e.getMessage());
            return ResponseEntity.status(500).body(erro);
        }
    }
    
        //gerar uma nova rota 
    @PostMapping("/gerar")
    public Rota gerarRotaViaParametros(
            @RequestParam String caminhaoId,
            @RequestParam List<String> bairrosVisitados,
            @RequestParam double distanciaTotal,
            @RequestParam List<String> tiposResiduos) {

        return rotaService.criarRota(caminhaoId, bairrosVisitados, distanciaTotal, tiposResiduos);
    }
    
    @PostMapping("/caminho-completo")
    public ResponseEntity<?> calcularCaminhoComDistanciaPost(@RequestBody CaminhoRequest request) {
        String origem = request.getOrigem();
        String destino = request.getDestino();

        List<Bairro> caminho = dijkstraService.calcularCaminho(origem, destino);
        double distancia = dijkstraService.calcularDistanciaTotal(origem, destino);

        if (caminho == null || caminho.isEmpty() || distancia < 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensagem", "Caminho não encontrado"));
        }

        List<String> nomes = caminho.stream()
                .map(Bairro::getNome)
                .toList();

        Map<String, Object> resposta = new LinkedHashMap<>();
        resposta.put("rota", nomes);
        resposta.put("distanciaKm", distancia);

        return ResponseEntity.ok(resposta);
    }

}
