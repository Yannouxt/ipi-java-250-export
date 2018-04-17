package com.example.demo.service;

import com.example.demo.dto.FactureDTO;
import com.example.demo.dto.LigneFactureDTO;
import com.example.demo.entity.Facture;
import com.example.demo.entity.LigneFacture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;

/**
 * Mapper pour transformer un Client en ClientDTO. Car on ne veut utiliser les objets Entity (JPA/Hibernate) en dehors de la couche service.
 */
@Component
public class FactureMapper {

    @Autowired
    private ClientMapper clientMapper;

    public FactureDTO map(Facture f) {
        FactureDTO factureDTO = new FactureDTO();
        factureDTO.setId(f.getId());
        factureDTO.setClient(clientMapper.map(f.getClient()));
        factureDTO.setLigneFactures(f.getLigneFactures().stream().map(this::mapLigneFacture).collect(toList()));
        return factureDTO;
    }

    private LigneFactureDTO mapLigneFacture(LigneFacture lf) {
        LigneFactureDTO ligneFactureDTO = new LigneFactureDTO();
        ligneFactureDTO.setDesignation(lf.getArticle().getLibelle());
        ligneFactureDTO.setQuantite(lf.getQuantite());
        ligneFactureDTO.setPrixUnitaire(lf.getArticle().getPrix());
        return ligneFactureDTO;
    }

}
