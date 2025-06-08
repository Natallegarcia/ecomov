package com.abelix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abelix.model.Rota;

@Repository
public interface RotaRepository extends JpaRepository<Rota, Long> {
}

