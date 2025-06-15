package com.abelix.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abelix.model.Rota;
import com.abelix.repository.RotaRepository;

@Service
public class RotaService {

    @Autowired
    private RotaRepository  rotaRepository;

  
    public Rota salvarRota(Rota rota) {
        return rotaRepository.save(rota);
    }

    public List<Rota> listarTodas() {
        return rotaRepository.findAll();
    }

    public Rota buscarPorId(Long id) {
        Optional<Rota> rotaOptional = rotaRepository.findById(id);
        if (rotaOptional.isPresent()) {
            return rotaOptional.get();
        } else {
            throw new RuntimeException("Rota não encontrada com ID: " + id);
        }
    }

    public Rota atualizarRota(Long id, Rota novaRota) {
        Optional<Rota> rotaExistenteOpt = rotaRepository.findById(id);

        if (rotaExistenteOpt.isPresent()) {
            Rota rotaExistente = rotaExistenteOpt.get();

            // Atualizando os campos 
            rotaExistente.setCaminhaoId(novaRota.getCaminhaoId());
            rotaExistente.setBairrosVisitados(novaRota.getBairrosVisitados());
            rotaExistente.setDistanciaTotal(novaRota.getDistanciaTotal());
            rotaExistente.setTiposResiduos(novaRota.getTiposResiduos());

            // Salvando no banco
            return rotaRepository.save(rotaExistente);
        } else {
            throw new RuntimeException("Rota com ID " + id + " não encontrada.");
        }
    }

    // Deletar rota
    public void deletarRota(Long id) {
        Rota rota = rotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rota não encontrada com ID: " + id));
        rotaRepository.delete(rota);
    }
}
