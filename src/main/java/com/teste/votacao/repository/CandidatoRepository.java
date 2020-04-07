package com.teste.votacao.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.teste.votacao.model.Candidato;

public interface CandidatoRepository extends CrudRepository<Candidato, Long>{
	List<Candidato> findByCargo_id(Long cargo_id);
}
