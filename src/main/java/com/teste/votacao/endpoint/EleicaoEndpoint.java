package com.teste.votacao.endpoint;

import java.util.Iterator;

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
import com.teste.votacao.model.Cargo;
import com.teste.votacao.model.Eleicao;
import com.teste.votacao.repository.EleicaoRepository;

@RestController
@RequestMapping("eleicoes")
public class EleicaoEndpoint {
	private final EleicaoRepository eleicaoDOA;
	
	@Autowired
	public EleicaoEndpoint(EleicaoRepository eleicaoDOA) {
		this.eleicaoDOA = eleicaoDOA;
	}
	
	@GetMapping
	public ResponseEntity<?> listaTudo( ) {
		return new ResponseEntity<>(eleicaoDOA.findAll(), HttpStatus.OK);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<?> getEleicaoPorId(@PathVariable("id") Long id) {
		verificaSeEleicaoExiste(id);
		Eleicao eleicao = eleicaoDOA.findById(id).get(); 
		return new ResponseEntity<>(eleicao, HttpStatus.OK);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> salvar(@Valid @RequestBody Eleicao eleicao) {
		return new ResponseEntity<>(eleicaoDOA.save(eleicao), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		verificaSeEleicaoExiste(id);
		eleicaoDOA.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<?> editar(@Valid @RequestBody Eleicao eleicao) {
		verificaSeEleicaoExiste(eleicao.getId());
		eleicaoDOA.save(eleicao);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//Metodo de validação se existe uma eleição pesquisada
	private void verificaSeEleicaoExiste(Long id) {
		if (!eleicaoDOA.findById(id).isPresent())
			throw new ResourceNotFoundException("A eleição com ID: " + id + " não existe");
	}
}
