package com.teste.votacao.endpoint;

import java.net.URI;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.teste.votacao.error.ResourceNotFoundException;
import com.teste.votacao.model.Eleicao;
import com.teste.votacao.repository.EleicaoRepository;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
public class EleicaoEndpoint {
	private final EleicaoRepository eleicaoDOA;
	
	@Autowired
	public EleicaoEndpoint(EleicaoRepository eleicaoDOA) {
		this.eleicaoDOA = eleicaoDOA;
	}
	
	@GetMapping(path = "/v1/eleicoes")
	public List<Eleicao> listaTudo( ) {
		return eleicaoDOA.findAll();
	}
	
	@GetMapping(path = "/eleicoes/{id}")
	public ResponseEntity<?> getEleicaoPorId(@PathVariable("id") Long id) {
		verificaSeEleicaoExiste(id);
		Eleicao eleicao = eleicaoDOA.findById(id).get(); 
		return new ResponseEntity<>(eleicao, HttpStatus.OK);
	}
	
	@PostMapping(path = "/v1/eleicoes")
	@Transactional
	public ResponseEntity<Void> salvar(@Valid @RequestBody Eleicao eleicao) {
		
		eleicao.setId(null);
		Eleicao eleicaoComment = eleicaoDOA.save(eleicao);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(eleicaoComment.getId())
				.toUri();
		
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@DeleteMapping("/v1/eleicoes/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		verificaSeEleicaoExiste(id);
		eleicaoDOA.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping(path = "/eleicoes")
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
