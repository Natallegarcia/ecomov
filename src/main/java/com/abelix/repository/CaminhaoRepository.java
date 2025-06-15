package com.abelix.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abelix.model.Caminhao;

@Repository
public interface CaminhaoRepository  extends JpaRepository<Caminhao, Long> {
    Optional<Caminhao> findByPlaca(String placa);
}
