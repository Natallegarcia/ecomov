package com.abelix.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abelix.DTO.ItinerarioCalendarioDTO;
import com.abelix.model.Caminhao;
import com.abelix.model.Itinerario;
import com.abelix.model.Rota;
import com.abelix.repository.CaminhaoRepository;
import com.abelix.repository.ItinerarioRepository;
import com.abelix.repository.RotaRepository;

@Service
public class ItinerarioService {

    @Autowired
    private ItinerarioRepository itinerarioRepository;

    @Autowired
    private CaminhaoRepository caminhaoRepository;

    @Autowired
    private RotaRepository rotaRepository;

    public List<Itinerario> listarTodos() {
        return itinerarioRepository.findAll();
    }

    public Optional<Itinerario> buscarPorId(Long id) {
        return itinerarioRepository.findById(id);
    }

    public void excluir(Long id) throws Exception {
        if (!itinerarioRepository.existsById(id)) {
            throw new Exception("Itinerário não encontrado.");
        }
        itinerarioRepository.deleteById(id);
    }

    public Itinerario atualizar(Itinerario itinerario) throws Exception {
        if (!itinerarioRepository.existsById(itinerario.getId())) {
            throw new Exception("Itinerário não encontrado.");
        }
        return itinerarioRepository.save(itinerario);
    }

    public List<Itinerario> buscarPorCaminhao(Long caminhaoId) {
        return itinerarioRepository.findByCaminhaoId(caminhaoId);
    }

    public List<Itinerario> buscarPorData(LocalDate dataColeta) {
        return itinerarioRepository.findByDataColeta(dataColeta);
    }

    public Itinerario criarItinerario(Itinerario itinerario) throws Exception {
        List<Itinerario> existentes = itinerarioRepository.findByCaminhaoIdAndDataColeta(
                itinerario.getCaminhaoId(), itinerario.getDataColeta());

        if (!existentes.isEmpty()) {
            throw new Exception("Já existe um itinerário para este caminhão nesta data.");
        }

        Optional<Caminhao> caminhaoOpt = caminhaoRepository.findById(itinerario.getCaminhaoId());
        if (caminhaoOpt.isEmpty()) {
            throw new Exception("Caminhão não encontrado.");
        }

        Optional<Rota> rotaOpt = rotaRepository.findById(itinerario.getRotaId());
        if (rotaOpt.isEmpty()) {
            throw new Exception("Rota não encontrada.");
        }

        return itinerarioRepository.save(itinerario);
    }

    public List<ItinerarioCalendarioDTO> listarItinerariosPorCaminhaoNoMes(Long caminhaoId, int ano, int mes) {
        YearMonth anoMes = YearMonth.of(ano, mes);
        LocalDate inicio = anoMes.atDay(1);
        LocalDate fim = anoMes.atEndOfMonth();

        List<Itinerario> itinerarios = itinerarioRepository.findByCaminhaoIdAndDataColetaBetween(caminhaoId, inicio, fim);

        List<ItinerarioCalendarioDTO> resultado = new ArrayList<>();

        for (LocalDate dia = inicio; !dia.isAfter(fim); dia = dia.plusDays(1)) {
            final LocalDate diaAtual = dia;  

            List<String> rotasDoDia = itinerarios.stream()
                    .filter(i -> i.getDataColeta().equals(diaAtual))  
                    .map(i -> "Rota ID: " + i.getRotaId())
                    .collect(Collectors.toList());

            resultado.add(new ItinerarioCalendarioDTO(diaAtual, rotasDoDia));
        }

        return resultado;
    }
    
    
}
