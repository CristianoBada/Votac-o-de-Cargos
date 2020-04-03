package com.teste.votacao.repository;

import org.springframework.data.repository.CrudRepository;

import com.teste.votacao.model.Eleitor;

public interface EleitorRepository extends CrudRepository<Eleitor, Long>{

}
