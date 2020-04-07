package com.teste.votacao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.teste.votacao.model.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, Long>{
	List<Cargo> findByEleicao_id(Long eleicao_id);
}
