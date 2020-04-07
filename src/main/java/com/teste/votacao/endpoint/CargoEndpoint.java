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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.teste.votacao.error.ResourceNotFoundException;
import com.teste.votacao.model.Cargo;
import com.teste.votacao.repository.CargoRepository;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
public class CargoEndpoint {
	
	private final CargoRepository cargoDOA;

	@Autowired
	public CargoEndpoint(CargoRepository cargoDOA) {
		this.cargoDOA = cargoDOA;
	}

	@GetMapping(path = "/v1/cargos")
	public List<Cargo> listaTudo() {
		return cargoDOA.findAll();
	}

	@GetMapping(path = "/v1/cargos/{id}")
	public ResponseEntity<?> getCargoPorId(@PathVariable("id") Long id) {
		verificaSeCargoExiste(id);
		Cargo cargo = cargoDOA.findById(id).get();
		return new ResponseEntity<>(cargo, HttpStatus.OK);
	}

	@PostMapping(path = "/v1/cargos")
	@Transactional
	public ResponseEntity<Void>  salvar(@Valid @RequestBody Cargo cargo) {	
		cargo.setId(null);
		
		Cargo cargoComment = cargoDOA.save(cargo);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cargoComment.getId())
				.toUri();
		
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@DeleteMapping("/v1/cargos/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		verificaSeCargoExiste(id);
		cargoDOA.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping(path = "/v1/cargos")
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
