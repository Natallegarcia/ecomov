package com.abelix.repository;


import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abelix.model.Bairro;
import com.abelix.model.Conexao;

@Repository
public interface ConexaoRepository extends JpaRepository<Conexao, Long> {

  //  List<Conexao> findByOrigem(Bairro origem);
    //List<Conexao> findByDestino(Bairro destino);


	List<Conexao> findByOrigemAndDestino(Bairro origem, Bairro destino);
	List<Conexao> findByOrigem(Bairro origem);

}
