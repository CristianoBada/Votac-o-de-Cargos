package com.teste.votacao.endpoint;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teste.votacao.model.Eleicao;
import com.teste.votacao.report.GeneratePdfReport;
import com.teste.votacao.repository.EleicaoRepository;

@RestController
public class RelatorioEndpoint {

	private final EleicaoRepository eleicaoDAO;
	
	@Autowired
	public RelatorioEndpoint(EleicaoRepository eleicaoDAO) {
		this.eleicaoDAO = eleicaoDAO;
	}
	
	
	@GetMapping(value = "/relatorioCompleto", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<?> relatorioCompleto() {
		
		List<Eleicao> eleicoes = eleicaoDAO.findAll();
		
		 ByteArrayInputStream bis = GeneratePdfReport.eleicoesReport(eleicoes);
		 
		 HttpHeaders headers = new HttpHeaders();
		 headers.add("Content-Disposition", "inline; filename=Elecoes.pdf");
		
		return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));

	}
	
	@GetMapping(value = "/relatorioFinal", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<?> relatorioElecoesFinalizadas() {
		
		List<Eleicao> eleicoes = eleicaoDAO.findByFimLessThanEqual(new Date());
		
		 ByteArrayInputStream bis = GeneratePdfReport.eleicoesReport(eleicoes);
		 
		 HttpHeaders headers = new HttpHeaders();
		 headers.add("Content-Disposition", "inline; filename=ElecoesFinalizadas.pdf");
		
		return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));

	}
	
	@GetMapping(value = "/relatorioParcial", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<?> relatorioElecoesEmAndamento() {
		
		List<Eleicao> eleicoes = eleicaoDAO.findByFimGreaterThanEqualAndInicioLessThanEqual(new Date(), new Date());
		
		 ByteArrayInputStream bis = GeneratePdfReport.eleicoesReport(eleicoes);
		
		 HttpHeaders headers = new HttpHeaders();
		 headers.add("Content-Disposition", "inline; filename=ElecoesEmAndamento.pdf");
		
		return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));

	}
}
