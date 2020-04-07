package com.teste.votacao.report;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.teste.votacao.model.Eleicao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class GeneratePdfReport {
	private static final Logger logger = LoggerFactory.getLogger(GeneratePdfReport.class);

	public static ByteArrayInputStream eleicoesReport(List<Eleicao> eleicoes) {

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			PdfWriter.getInstance(document, out);
			document.open();

			if (eleicoes.size() == 0) {
				document.add(new Paragraph("Não existe eleições!"));
			}

			for (Eleicao e : eleicoes) {
				document.add(new Paragraph(e.getNome() + " Data inicio: " + e.getInicio() + " Data final: " + e.getFim()));

			
			}

			document.close();

		} catch (DocumentException ex) {
			logger.error("Error occurred: {0}", ex);
		}

		return new ByteArrayInputStream(out.toByteArray());

	}
}
