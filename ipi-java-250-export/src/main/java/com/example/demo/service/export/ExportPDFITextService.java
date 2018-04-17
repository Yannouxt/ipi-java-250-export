package com.example.demo.service.export;

import java.io.IOException;

import javax.servlet.ServletOutputStream;

import org.springframework.stereotype.Service;

import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.FactureDTO;
import com.example.demo.dto.LigneFactureDTO;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class ExportPDFITextService {

	public void export(ServletOutputStream outputStream, FactureDTO facture) throws IOException, DocumentException {
        
		ClientDTO client = facture.getClient();
		Double montantTotal = 0.0;
		Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();
        
        document.add(new Paragraph("Facture de "+client.getNom()+" "+client.getPrenom()));
        document.add(Chunk.NEWLINE );
        
        PdfPTable table = new PdfPTable(3);
        table.addCell("Libelle");
        table.addCell("Quantité");
        table.addCell("Prix");
		

		for(LigneFactureDTO ligneFacture : facture.getLigneFactures()){
			String libelle = ligneFacture.getDesignation();
			int quantite = ligneFacture.getQuantite();
			Double total = ligneFacture.getPrixUnitaire()*quantite;
			montantTotal+= total;
			
	        table.addCell(libelle);
	        table.addCell(Integer.toString(quantite));
	        table.addCell(Double.toString(total));
		}
		
		table.addCell("Total");
	    // we add a cell with colspan 2
        PdfPCell cell = new PdfPCell(new Phrase(Double.toString(montantTotal)));
        cell.setColspan(2);
        table.addCell(cell);
        
        document.add(table);
        document.close();
	}

}

