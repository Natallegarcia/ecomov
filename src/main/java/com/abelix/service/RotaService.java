package com.abelix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abelix.model.Rota;
import com.abelix.repository.RotaRepository;

@Service
public class RotaService {

    @Autowired
    private RotaRepository rotaRepository;

    // Método usado diretamente com a entidade pronta (por exemplo, vindo do @RequestBody)
    public Rota salvarRota(Rota rota) {
        return rotaRepository.save(rota);
    }

    // ✅ Novo método sugerido: criação de rota com parâmetros simples
    public Rota criarRota(String caminhaoId, List<String> bairrosVisitados, double distanciaTotal, List<String> tiposResiduos) {
        Rota novaRota = new Rota(caminhaoId, bairrosVisitados, distanciaTotal, tiposResiduos);
        return rotaRepository.save(novaRota);
    }

    public List<Rota> listarTodas() {
        return rotaRepository.findAll();
    }

    public Rota buscarPorId(Long id) {
        return rotaRepository.findById(id).orElse(null);
    }
}
