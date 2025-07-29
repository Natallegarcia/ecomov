package com.abelix.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.abelix.DTO.ItinerarioCalendarioDTO;
import com.abelix.commons.TipoResiduo;
import com.abelix.model.Caminhao;
import com.abelix.model.Itinerario;
import com.abelix.model.Rota;
import com.abelix.repository.CaminhaoRepository;
import com.abelix.repository.ItinerarioRepository;
import com.abelix.repository.RotaRepository;
import com.abelix.service.RotaService;

@Service
public class ItinerarioService {

    @Autowired
    private ItinerarioRepository itinerarioRepository;

    @Autowired
    private CaminhaoRepository caminhaoRepository;
    
    @Autowired
    private RotaService rotaService;

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

    public Itinerario criarItinerario(Itinerario itinerario) {
        // Verifica se já existe itinerário para o caminhão na mesma data
        List<Itinerario> existentes = itinerarioRepository.findByCaminhaoIdAndDataColeta(
                itinerario.getCaminhaoId(), itinerario.getDataColeta());

        if (!existentes.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Já existe um itinerário para este caminhão nesta data.");
        }

        // Busca caminhão no banco
        Caminhao caminhao = caminhaoRepository.findById(itinerario.getCaminhaoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Caminhão não encontrado."));

        // Busca rota no banco
        Rota rota = rotaRepository.findById(itinerario.getRotaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rota não encontrada."));

        // Valida peso estimado da rota
        Double pesoEstimado = rota.getPesoEstimado();
        if (pesoEstimado == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "O peso estimado da rota deve ser informado e maior que zero.");
        }
        if (pesoEstimado <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "O peso estimado da rota deve ser maior que zero.");
        }

        // Verifica se o caminhão suporta o peso da rota
        if (pesoEstimado > caminhao.getCapacidadeCarga()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A carga da rota excede a capacidade do caminhão.");
        }

        // Verifica se o caminhão suporta todos os tipos de resíduos da rota
        for (String tipoStr : rota.getTiposResiduos()) {
            TipoResiduo tipo = TipoResiduo.valueOf(tipoStr);
            if (!caminhao.getTiposResiduos().contains(tipo)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "O caminhão não é compatível com o tipo de resíduo: " + tipo);
            }
        }

        // Salva e retorna o itinerário
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
