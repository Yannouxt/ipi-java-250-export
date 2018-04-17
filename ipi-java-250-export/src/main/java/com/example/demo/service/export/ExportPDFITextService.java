package com.example.demo.service.export;

import java.io.IOException;

import javax.servlet.ServletOutputStream;

import org.springframework.stereotype.Service;

import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.FactureDTO;
import com.example.demo.dto.LigneFactureDTO;
import com.example.demo.entity.LigneFacture;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class ExportPDFITextService {

	public void export(ServletOutputStream outputStream, FactureDTO facture) throws IOException, DocumentException {
        
		ClientDTO client = facture.getClient();
		Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();
        
        document.add(new Paragraph("Facture de "+client.getNom()+" "+client.getPrenom()));
        document.add(Chunk.NEWLINE );
        
        PdfPTable table = new PdfPTable(facture.getLigneFactures().size());
        table.addCell("Libelle"+ "................."+"Quantité"+"..................."+"Prix");
		

		for(LigneFactureDTO ligneFacture : facture.getLigneFactures()){
			String libelle = ligneFacture.getDesignation();
			int quantite = ligneFacture.getQuantite();
			Double total = ligneFacture.getPrixUnitaire()*quantite;
			table.addCell(libelle+ "................."+quantite+"..................."+total);
		}
        document.add(table);

		

        document.close();
	}

}

