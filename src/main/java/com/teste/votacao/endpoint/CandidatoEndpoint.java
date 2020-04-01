package com.teste.votacao.endpoint;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teste.votacao.error.ResourceNotFoundException;
import com.teste.votacao.model.Candidato;
import com.teste.votacao.repository.CandidatoRepository;

@RestController
@RequestMapping("candidatos")
public class CandidatoEndpoint {
	private final CandidatoRepository candidatoDOA;

	@Autowired
	public CandidatoEndpoint(CandidatoRepository candidatoDOA) {
		this.candidatoDOA = candidatoDOA;
	}

	@GetMapping
	public ResponseEntity<?> listaTudo() {
		return new ResponseEntity<>(candidatoDOA.findAll(), HttpStatus.OK);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<?> getCandidatoPorId(@PathVariable("id") Long id) {
		verificaSeEleicaoExiste(id);
		Candidato candidato = candidatoDOA.findById(id).get();
		return new ResponseEntity<>(candidato, HttpStatus.OK);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<?> salvar(@Valid @RequestBody Candidato candidato) {
		return new ResponseEntity<>(candidatoDOA.save(candidato), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		verificaSeEleicaoExiste(id);
		candidatoDOA.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<?> editar(@Valid @RequestBody Candidato candidato) {
		verificaSeEleicaoExiste(candidato.getId());
		candidatoDOA.save(candidato);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// Metodo de validação se existe uma eleição pesquisada
	private void verificaSeEleicaoExiste(Long id) {
		if (!candidatoDOA.findById(id).isPresent())
			throw new ResourceNotFoundException("O candidato com ID: " + id + " não existe");
	}
}
