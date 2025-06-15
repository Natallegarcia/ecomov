package com.abelix.repository;

import com.abelix.model.Itinerario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository

public interface ItinerarioRepository extends JpaRepository<Itinerario, Long> {

	List<Itinerario> findByCaminhaoIdAndDataColeta(Long caminhaoId, LocalDate dataColeta);
    List<Itinerario> findByDataColeta(LocalDate dataColeta);
    List<Itinerario> findByRotaId(Long rotaId);
    List<Itinerario> findByCaminhaoId(Long caminhaoId);
    List<Itinerario> findByDataColetaBetween(LocalDate inicio, LocalDate fim);
    List<Itinerario> findByCaminhaoIdAndDataColetaBetween(Long caminhaoId, LocalDate inicio, LocalDate fim);
}
