package com.teste.votacao.endpoint;

import java.lang.module.FindException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teste.votacao.model.Candidato;
import com.teste.votacao.model.Cargo;
import com.teste.votacao.model.Eleicao;
import com.teste.votacao.repository.CandidatoRepository;
import com.teste.votacao.repository.CargoRepository;
import com.teste.votacao.repository.EleicaoRepository;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
public class RelatorioEndpoint {

	private final EleicaoRepository eleicaoDAO;
	private final CargoRepository cargoDAO;
	private final CandidatoRepository candidatoDAO;
	
	@Autowired
	public RelatorioEndpoint(EleicaoRepository eleicaoDAO, CargoRepository cargoDAO, CandidatoRepository candidatoDAO) {
		this.eleicaoDAO = eleicaoDAO;
		this.cargoDAO = cargoDAO;
		this.candidatoDAO = candidatoDAO;
	}
	
	@GetMapping(value = "/v1/relatorioFinal")
	public ResponseEntity<?> relatorioElecoesFinalizadas() {
		return new ResponseEntity<>(eleicaoDAO.findByFimLessThanEqual(new Date()), HttpStatus.OK);
	}
	
	@GetMapping(value = "/v1/relatorioParcial")
	public ResponseEntity<?> relatorioElecoesEmAndamento() {
		Date date = new Date();
		List<Cargo> cargos = new ArrayList<>();
		List<Candidato> candidatos = new ArrayList<>();
		for (Eleicao eleicao : eleicaoDAO.findByFimGreaterThanEqualAndInicioLessThanEqual(date, date)) {
			cargos.addAll(cargoDAO.findByEleicao_id(eleicao.getId()));
			System.out.println(eleicao.getNome());
			for (Cargo cargo: cargoDAO.findByEleicao_id(eleicao.getId())) {
				System.out.println(cargo.getNome());
				candidatos = candidatoDAO.findByCargo_id(cargo.getId());
				for (Candidato candidato :  candidatoDAO.findByCargo_id(cargo.getId())) {
					System.out.println(candidato.getNome());
				}
			}
			
		}
		
		
		return new ResponseEntity<>(eleicaoDAO.findByFimGreaterThanEqualAndInicioLessThanEqual(date, date), HttpStatus.OK);
	}
}
