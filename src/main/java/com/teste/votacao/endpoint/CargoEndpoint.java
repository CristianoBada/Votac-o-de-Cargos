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
import com.teste.votacao.model.Cargo;
import com.teste.votacao.repository.CargoRepository;

@RestController
@RequestMapping("cargos")
public class CargoEndpoint {
	private final CargoRepository cargoDOA;

	@Autowired
	public CargoEndpoint(CargoRepository cargoDOA) {
		this.cargoDOA = cargoDOA;
	}

	@GetMapping
	public ResponseEntity<?> listaTudo() {
		return new ResponseEntity<>(cargoDOA.findAll(), HttpStatus.OK);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<?> getCargoPorId(@PathVariable("id") Long id) {
		verificaSeCargoExiste(id);
		Cargo cargo = cargoDOA.findById(id).get();
		return new ResponseEntity<>(cargo, HttpStatus.OK);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<?> salvar(@Valid @RequestBody Cargo cargo) {
		return new ResponseEntity<>(cargoDOA.save(cargo), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		verificaSeCargoExiste(id);
		cargoDOA.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<?> editar(@Valid @RequestBody Cargo cargo) {
		verificaSeCargoExiste(cargo.getId());
		cargoDOA.save(cargo);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// Metodo de validação se existe um cargo pesquisado
	private void verificaSeCargoExiste(Long id) {
		if (!cargoDOA.findById(id).isPresent())
			throw new ResourceNotFoundException("O cargo com ID: " + id + " não existe");
	}
}
