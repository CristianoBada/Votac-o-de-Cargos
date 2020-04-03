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
import com.teste.votacao.model.Eleitor;
import com.teste.votacao.repository.EleitorRepository;
import com.teste.votacao.util.Protocolo;

@RestController
@RequestMapping("eleitores")
public class EleitorEndpoint {
private final EleitorRepository eleitorDOA;
	
	@Autowired
	public EleitorEndpoint(EleitorRepository eleitorDOA) {
		this.eleitorDOA = eleitorDOA;
	}
	
	@GetMapping
	public ResponseEntity<?> listaTudo( ) {
		return new ResponseEntity<>(eleitorDOA.findAll(), HttpStatus.OK);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<?> getEleitorPorId(@PathVariable("id") Long id) {
		verificaSeEleitorExiste(id);
		Eleitor eleitor = eleitorDOA.findById(id).get(); 
		return new ResponseEntity<>(eleitor, HttpStatus.OK);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> salvar(@Valid @RequestBody Eleitor eleitor) {
		eleitor.setProtocolo(new Protocolo().gerecaoDeCodigoAlfaNumerico());
		return new ResponseEntity<>(eleitorDOA.save(eleitor), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		verificaSeEleitorExiste(id);
		eleitorDOA.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<?> editar(@Valid @RequestBody Eleitor eleitor) {
		verificaSeEleitorExiste(eleitor.getId());
		eleitorDOA.save(eleitor);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//Metodo de validação se existe um eleitor pesquisado
	private void verificaSeEleitorExiste(Long id) {
		if (!eleitorDOA.findById(id).isPresent())
			throw new ResourceNotFoundException("A eleição com ID: " + id + " não existe");
	}
}
