package com.abelix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abelix.model.PontoColeta;

@Repository
public interface PontoColetaRepository extends JpaRepository<PontoColeta, Long> {
    boolean existsByNome(String nome);
}
