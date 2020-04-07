package com.teste.votacao.endpoint;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.teste.votacao.error.ResourceNotFoundException;
import com.teste.votacao.model.Candidato;
import com.teste.votacao.repository.CandidatoRepository;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
public class CandidatoEndpoint {
	private final CandidatoRepository candidatoDOA;

	@Autowired
	public CandidatoEndpoint(CandidatoRepository candidatoDOA) {
		this.candidatoDOA = candidatoDOA;
	}

	@GetMapping(path = "/v1/candidatos")
	public ResponseEntity<?> listaTudo() {
		return new ResponseEntity<>(candidatoDOA.findAll(), HttpStatus.OK);
	}

	@GetMapping(path = "/v1/candidatos/{id}")
	public ResponseEntity<?> getCandidatoPorId(@PathVariable("id") Long id) {
		verificaSeCandidatoExiste(id);
		Candidato candidato = candidatoDOA.findById(id).get();
		return new ResponseEntity<>(candidato, HttpStatus.OK);
	}

	@PostMapping(path = "/v1/candidatos")
	@Transactional
	public ResponseEntity<?> salvar(@Valid @RequestBody Candidato candidato) {
		return new ResponseEntity<>(candidatoDOA.save(candidato), HttpStatus.CREATED);
	}

	@DeleteMapping(path = "/v1/candidatos/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		verificaSeCandidatoExiste(id);
		candidatoDOA.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping(path = "/v1/candidatos")
	public ResponseEntity<?> editar(@Valid @RequestBody Candidato candidato) {
		verificaSeCandidatoExiste(candidato.getId());
		candidato.setVotos(0);
		candidatoDOA.save(candidato);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// Metodo de validação se existe um candidato pesquisado
	private void verificaSeCandidatoExiste(Long id) {
		if (!candidatoDOA.findById(id).isPresent())
			throw new ResourceNotFoundException("O candidato com ID: " + id + " não existe");
	}
}
