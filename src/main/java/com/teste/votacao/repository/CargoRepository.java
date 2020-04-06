package com.teste.votacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.teste.votacao.model.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, Long>{

}
