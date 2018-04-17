package com.example.demo.service.export;

import com.example.demo.dto.ClientDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;

@Component
public class ExportJSONService {

    @Autowired
    private ObjectMapper objectMapper;

    public void export(Writer writer, ClientDTO client) throws IOException {
        objectMapper.writeValue(writer, client);
    }
}
