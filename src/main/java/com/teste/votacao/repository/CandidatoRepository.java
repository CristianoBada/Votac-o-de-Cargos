package com.teste.votacao.repository;

import org.springframework.data.repository.CrudRepository;

import com.teste.votacao.model.Candidato;

public interface CandidatoRepository extends CrudRepository<Candidato, Long>{

}
