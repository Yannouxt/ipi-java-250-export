package com.example.demo.service;

import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.FactureDTO;
import com.example.demo.entity.Client;
import com.example.demo.entity.Facture;
import com.example.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientMapper clientMapper;

    public List<ClientDTO> findAllClients() {
        return clientRepository.findAll().stream().map(c-> clientMapper.map(c)).collect(toList());
    }
    
    private ClientDTO toDTO(Client c) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(c.getId());
        clientDTO.setNom(c.getNom());
        clientDTO.setPrenom(c.getPrenom());
        return clientDTO;
    }

    
    public ClientDTO findById(Long id){
        return clientRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() ->
                new IllegalArgumentException("Client inconnu " + id)
        );
    }
}
