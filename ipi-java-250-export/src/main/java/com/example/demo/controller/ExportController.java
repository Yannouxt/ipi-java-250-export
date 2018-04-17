package com.example.demo.controller;

import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.FactureDTO;
import com.example.demo.service.ClientService;
import com.example.demo.service.FactureService;
import com.example.demo.service.export.ExportCSVService;
import com.example.demo.service.export.ExportPDFBirtService;
import com.example.demo.service.export.ExportPDFITextService;
import com.example.demo.service.export.ExportXLSXService;
import com.itextpdf.text.DocumentException;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.assertj.core.util.Files;
import org.eclipse.birt.report.engine.api.EngineException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("/")
public class ExportController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ExportCSVService exportCSVService;

    @Autowired
    private FactureService factureService;

    @Autowired
    private ExportPDFITextService exportPDFITextService;
    
    @Autowired
    private ExportXLSXService exportXSLXService;
    
    @Autowired
    private ExportPDFBirtService exportPDFBirtService;
    
    
    

    @GetMapping("/clients/csv")
    public void clientsCSV(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"clients.csv\"");
        List<ClientDTO> clients = clientService.findAllClients();
        exportCSVService.export(response.getWriter(), clients);
    }

    @GetMapping("/factures/{id}/pdf")
    public void facturePDF(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"facture " + id + ".pdf\"");
        FactureDTO facture = factureService.findById(id);
        exportPDFITextService.export(response.getOutputStream(), facture);
    }
    
    @GetMapping("/clients/xlsx")
    	public void clientsXLSX(HttpServletRequest request, HttpServletResponse response) throws IOException{
        	response.setContentType("application/xlsx");
        	response.setHeader("Content-Disposition", "attachment; filename=\"clients.xlsx\"");
            List<ClientDTO> clients = clientService.findAllClients();
            exportXSLXService.export(response.getOutputStream(), clients);	
    }
    
    @GetMapping("/client/{id}/xlsx")
	public void clientXLSX(@PathVariable("id") Long id,HttpServletRequest request, HttpServletResponse response) throws IOException{
    	response.setContentType("application/xlsx");
    	response.setHeader("Content-Disposition", "attachment; filename=\"Facturesclient"+id+".xlsx\"");
        List<FactureDTO> factures = factureService.findFacturesByClient(id);
        exportXSLXService.exportFacturesClient(response.getOutputStream(),factures);	
    }
    
    @GetMapping("/factures/{id}/pdfBirt")
    public void facturePDFBirt(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
//        response.setContentType("application/pdf");
//        response.setHeader("Content-Disposition", "attachment; filename=\"facture " + id + ".pdf\"");
        FactureDTO facture = factureService.findById(id);
        try {
			exportPDFBirtService.export(response.getOutputStream(), facture);
		} catch (EngineException e) {
			e.printStackTrace();
		}
    }
    

}
