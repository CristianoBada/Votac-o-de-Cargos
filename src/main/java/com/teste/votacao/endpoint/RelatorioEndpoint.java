package com.teste.votacao.endpoint;

import java.io.ByteArrayInputStream;
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
	
	
	@GetMapping(value = "/pdfreport", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<?> relatorioCompleto() {
		
		List<Eleicao> eleicoes = (List<Eleicao>) eleicaoDAO.findAll();
		
		 ByteArrayInputStream bis = GeneratePdfReport.eleicoesReport(eleicoes);
		 
		 HttpHeaders headers = new HttpHeaders();
		 headers.add("Content-Disposition", "inline; filename=elecoesreport.pdf");
		
		return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));

	}
}
