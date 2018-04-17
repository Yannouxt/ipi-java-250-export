package com.example.demo.service;

import com.example.demo.dto.ClientDTO;
import com.example.demo.entity.Client;
import org.springframework.stereotype.Component;

/**
 * Mapper pour transformer un Client en ClientDTO. Car on ne veut utiliser les objets Entity (JPA/Hibernate) en dehors de la couche service.
 */
@Component
public class ClientMapper {
    public ClientDTO map(Client entity) {
        ClientDTO dto = new ClientDTO();
        dto.setId(entity.getId());
        dto.setNom(entity.getNom());
        dto.setPrenom(entity.getPrenom());
        return dto;
    }
}
