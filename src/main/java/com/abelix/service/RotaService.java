package com.abelix.service;


import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abelix.commons.TipoResiduo;
import com.abelix.model.Caminhao;
import com.abelix.model.Rota;
import com.abelix.repository.RotaRepository;
import com.abelix.repository.CaminhaoRepository;

@Service
public class RotaService {

    @Autowired
    private RotaRepository  rotaRepository;
    
    @Autowired
    private CaminhaoRepository caminhaoRepository;


  
    public Rota salvarRota(Rota rota) {
    	  validarCaminhaoParaRota(rota);
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
    
    
    private void validarCaminhaoParaRota(Rota rota) {
        // Converter caminhaoId (String) para Long para buscar no repository
        Long caminhaoIdLong;
        try {
            caminhaoIdLong = Long.parseLong(rota.getCaminhaoId());
        } catch (NumberFormatException e) {
            throw new RuntimeException("ID do caminhão inválido: " + rota.getCaminhaoId());
        }

        Caminhao caminhao = caminhaoRepository.findById(caminhaoIdLong)
            .orElseThrow(() -> new RuntimeException("Caminhão com ID " + caminhaoIdLong + " não encontrado."));

        // Valida tipos de resíduos da rota (List<String>) convertendo para Set<TipoResiduo>
        Set<TipoResiduo> residuosRota;
        try {
            residuosRota = rota.getTiposResiduos().stream()
                .map(TipoResiduo::valueOf) // converte string para enum
                .collect(Collectors.toSet());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter tipos de resíduos da rota. Verifique os valores.");
        }

        Set<TipoResiduo> residuosCaminhao = caminhao.getTiposResiduos();

        if (residuosRota.size() != 1) {
            throw new RuntimeException("A rota deve conter exatamente UM tipo de resíduo.");
        }

        TipoResiduo tipoRota = residuosRota.iterator().next();

        if (!residuosCaminhao.contains(tipoRota)) {
            throw new RuntimeException("O caminhão informado não está habilitado para transportar o tipo de resíduo da rota: " + tipoRota);
        }

        // Valida se bairrosVisitados existe e não está vazio
        if (rota.getBairrosVisitados() == null || rota.getBairrosVisitados().isEmpty()) {
            throw new RuntimeException("A rota deve conter ao menos um bairro visitado.");
        }

        // Valida pesoEstimado contra capacidade do caminhão
        if (rota.getPesoEstimado() == null || rota.getPesoEstimado() <= 0) {
            throw new RuntimeException("O peso estimado da rota deve ser informado e maior que zero.");
        }

        if (rota.getPesoEstimado() > caminhao.getCapacidadeCarga()) {
            throw new RuntimeException("O peso estimado da rota (" + rota.getPesoEstimado()
                    + " kg) excede a capacidade do caminhão (" + caminhao.getCapacidadeCarga() + " kg).");
        }
    }

    
}
