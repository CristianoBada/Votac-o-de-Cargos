package com.teste.votacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

import com.teste.votacao.model.Eleicao;

public interface EleicaoRepository extends JpaRepository<Eleicao, Long>{
	
	List<Eleicao> findByFimGreaterThanEqualAndInicioLessThanEqual(Date dataAtual1, Date dataAtual2);
	
	List<Eleicao> findByFimLessThanEqual(Date dataAtual);
	
}
