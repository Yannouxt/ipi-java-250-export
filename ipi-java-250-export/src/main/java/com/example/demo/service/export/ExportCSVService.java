package com.example.demo.service.export;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ClientDTO;


@Service
public class ExportCSVService {

    //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ";";
    private static final String NEW_LINE_SEPARATOR = "\n";

    //CSV file header
    private static final String FILE_HEADER = "Nom;Prénom";

	public void export(Writer printwriter, List<ClientDTO> clients) throws IOException{
		printwriter.write(FILE_HEADER);
		printwriter.write(NEW_LINE_SEPARATOR);
		
		for(ClientDTO client : clients){
			printwriter.write(this.format(client.getNom()));
			printwriter.write(COMMA_DELIMITER);
			printwriter.write(this.format(client.getPrenom()));
			printwriter.write(NEW_LINE_SEPARATOR);
		}
	}
	
	private String format(String text){
		ArrayList<Integer>pos = new ArrayList<Integer>();
		for (int i = -1; (i = text.indexOf('"', i + 1)) != -1; i++) {
		    pos.add(i);
		}
		for(int i=0; i<pos.size();i++){
			text = new StringBuilder(text).insert(pos.get(i).intValue(), '"').toString();
		}
		return '"'+text+'"';
	}
}
