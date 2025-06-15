package com.abelix.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abelix.model.UsuarioModel;

public interface UsuarioRepository extends JpaRepository<UsuarioModel,Integer > {
	public Optional<UsuarioModel> findByLogin (String login);
	

}
