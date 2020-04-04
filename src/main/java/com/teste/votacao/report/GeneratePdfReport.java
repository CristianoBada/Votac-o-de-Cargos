package com.teste.votacao.report;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
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
			PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(60);
            
            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            
            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("Id", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Nome", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);
            
            hcell = new PdfPCell(new Phrase("inicio", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);
            
            hcell = new PdfPCell(new Phrase("fim", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);
            
            
            for (Eleicao e : eleicoes) {
            	 PdfPCell cell;
            	 cell = new PdfPCell(new Phrase(e.getId().toString()));
                 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                 table.addCell(cell);

                 cell = new PdfPCell(new Phrase(e.getNome()));
                 cell.setPaddingLeft(5);
                 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                 table.addCell(cell);
                 
                 cell = new PdfPCell(new Phrase(e.getInicio().toString()));
                 cell.setPaddingLeft(5);
                 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                 table.addCell(cell);
                 
                 cell = new PdfPCell(new Phrase(e.getFim().toString()));
                 cell.setPaddingLeft(5);
                 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                 table.addCell(cell);
            }
            
            PdfWriter.getInstance(document, out);
            document.open();
            document.add(table);

            document.close();
            
		} catch (DocumentException ex) {
			 logger.error("Error occurred: {0}", ex);
		}

		return new ByteArrayInputStream(out.toByteArray());

	}
}
