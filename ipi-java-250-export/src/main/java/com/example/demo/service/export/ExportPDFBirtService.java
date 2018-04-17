package com.example.demo.service.export;


import com.example.demo.dto.FactureDTO;
import com.itextpdf.text.DocumentException;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.*;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;

@Service
public class ExportPDFBirtService {
	EngineConfig config;
	public ExportPDFBirtService(){
		this.config = new EngineConfig();
        try {
			Platform.startup(config);
		} catch (BirtException e) {
			e.printStackTrace();
		}

	}

    public void export(ServletOutputStream outputStream, FactureDTO factureDTO) throws DocumentException, EngineException, FileNotFoundException {
        try {
            final IReportEngineFactory FACTORY = (IReportEngineFactory) Platform
                    .createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
            IReportEngine engine = FACTORY.createReportEngine(config);

            IReportRunnable design = engine.openReportDesign("./src/main/resources/facture_report.rptdesign");
            IRunAndRenderTask task = engine.createRunAndRenderTask(design);

            HashMap<String, Object> contextMap = new HashMap<String, Object>();
            contextMap.put("org.eclipse.birt.report.data.oda.xml.inputStream", new FileInputStream("./src/main/resources/birtExample.xml"));
            contextMap.put("org.eclipse.birt.report.data.oda.xml.closeInputStream", new Boolean(true));
            task.setAppContext(contextMap);

            PDFRenderOption PDF_OPTIONS = new PDFRenderOption();
            PDF_OPTIONS.setOutputFileName("./src/main/resources/test.pdf");
            PDF_OPTIONS.setOutputFormat("pdf");

            task.setRenderOption(PDF_OPTIONS);
            task.run();
            task.close();
            engine.destroy();
        } catch (final Exception EX) {
            EX.printStackTrace();
        } 
    }
}
