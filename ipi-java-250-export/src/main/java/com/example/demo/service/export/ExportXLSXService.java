package com.example.demo.service.export;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFRow;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.FactureDTO;
import com.example.demo.dto.LigneFactureDTO;

@Service
public class ExportXLSXService {
	
	public void export(OutputStream os, List<ClientDTO> clients) throws IOException{
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("clients");
	    XSSFRow row;
	    int rowid = 2;
	    
        row = sheet.createRow(rowid++);
        
        Cell cellNom = row.createCell(2);
        cellNom.setCellValue("Nom");
        CellStyle style =this.borderStyleCell(workbook, cellNom);
        this.colorAndFontStyleCell(workbook, cellNom, style);

        Cell cellPrenom = row.createCell(3);
        cellPrenom.setCellValue("Prénom");
        style = this.borderStyleCell(workbook, cellPrenom);
        this.colorAndFontStyleCell(workbook, cellPrenom, style);


	    for (ClientDTO client : clients) {
	         row = sheet.createRow(rowid++);

	         cellNom = row.createCell(2);
	         cellNom.setCellValue(client.getNom());
	         this.borderStyleCell(workbook, cellNom);

	         cellPrenom = row.createCell(3);
	         cellPrenom.setCellValue(client.getPrenom());
	         this.borderStyleCell(workbook, cellPrenom);	
	    }
	    sheet.autoSizeColumn(2);
	    sheet.autoSizeColumn(3);

		workbook.write(os);
		workbook.close();

	}
	public void exportFacturesClient(OutputStream os,List<FactureDTO> factures) throws IOException{
		
		XSSFWorkbook workbook = new XSSFWorkbook();
	    XSSFRow row;
	    int nbFactures = 1;

	    for (FactureDTO facture : factures) {
			 XSSFSheet sheet = workbook.createSheet("Facture "+nbFactures++);
			 int rowid = 2;
	         row = sheet.createRow(rowid++);

	         Cell cellLibelle = row.createCell(2);
	         cellLibelle.setCellValue("Désignation");
	         CellStyle style = this.borderStyleCell(workbook, cellLibelle);
	         this.colorAndFontStyleCell(workbook, cellLibelle, style);
	         
	         Cell cellQuantite = row.createCell(3);
	         cellQuantite.setCellValue("Quantité");
	         style = this.borderStyleCell(workbook, cellQuantite);
	         this.colorAndFontStyleCell(workbook, cellQuantite, style);
	         
	         Cell cellPrixUnite = row.createCell(4);
	         cellPrixUnite.setCellValue("Prix Unitaire");
	         style = this.borderStyleCell(workbook, cellPrixUnite);
	         this.colorAndFontStyleCell(workbook, cellPrixUnite, style);


	         Cell cellPrixTotal = row.createCell(5);
	         cellPrixTotal.setCellValue("Prix total"); 
	         style = this.borderStyleCell(workbook, cellPrixTotal);
	         this.colorAndFontStyleCell(workbook, cellPrixTotal, style);


			 for(LigneFactureDTO ligne : facture.getLigneFactures()){
		         row = sheet.createRow(rowid++);

		         cellLibelle = row.createCell(2);
		         cellLibelle.setCellValue(ligne.getDesignation());
		         this.borderStyleCell(workbook, cellLibelle);
		         
		         cellQuantite = row.createCell(3);
		         cellQuantite.setCellValue(ligne.getQuantite());
		         this.borderStyleCell(workbook, cellQuantite);
		         
		         cellPrixUnite = row.createCell(4);
		         cellPrixUnite.setCellValue(ligne.getPrixUnitaire());
		         this.borderStyleCell(workbook, cellPrixUnite);

		         cellPrixTotal = row.createCell(5);
		         cellPrixTotal.setCellValue(ligne.getPrixUnitaire()*ligne.getQuantite()); 
		         this.borderStyleCell(workbook, cellPrixTotal);

			 }
			 sheet.autoSizeColumn(2);
			 sheet.autoSizeColumn(3);
			 sheet.autoSizeColumn(4);
			 sheet.autoSizeColumn(5);
	    }
		workbook.write(os);
		workbook.close();		
	}
	
	public CellStyle borderStyleCell(XSSFWorkbook wb, Cell cell){
	    // Style the cell with borders all around.
	    CellStyle style = wb.createCellStyle();
	    style.setBorderBottom(BorderStyle.MEDIUM);
	    style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	    style.setBorderLeft(BorderStyle.MEDIUM);
	    style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	    style.setBorderRight(BorderStyle.MEDIUM);
	    style.setRightBorderColor(IndexedColors.BLACK.getIndex());
	    style.setBorderTop(BorderStyle.MEDIUM);
	    style.setTopBorderColor(IndexedColors.BLACK.getIndex());

	    cell.setCellStyle(style);
	    return style;
	}
	
	public void colorAndFontStyleCell(XSSFWorkbook wb, Cell cell, CellStyle style){
	    style.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
	    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    Font font = wb.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
	    cell.setCellStyle(style);

	}
}

