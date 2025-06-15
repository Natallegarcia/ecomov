package com.abelix.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abelix.model.Bairro;

@Repository
public interface BairroRepository extends  JpaRepository <Bairro, Long> {
    Optional<Bairro> findByNome(String nome);
}